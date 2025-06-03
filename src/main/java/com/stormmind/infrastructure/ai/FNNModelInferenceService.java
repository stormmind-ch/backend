package com.stormmind.infrastructure.ai;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.NDArray;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.repository.zoo.ModelZoo;
import com.stormmind.application.ai.ModelInferenceService;
import com.stormmind.domain.Inference;
import com.stormmind.domain.FNNModelInference;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FNNModelInferenceService implements ModelInferenceService {

    private ZooModel<FNNModelInference, Float> model;

    @PostConstruct
    public void init() throws ModelNotFoundException, MalformedModelException, IOException {
        Criteria<FNNModelInference, Float> criteria = Criteria.builder()
                .setTypes(FNNModelInference.class, Float.class)
                .optModelPath(Paths.get("models"))
                .optModelName("fnn_temp_sun_rain")
                .optTranslator(new FNNTranslator())
                .build();

        model = ModelZoo.loadModel(criteria);
    }

    /**
     * This method is used that the memory is freed after the application shuts down, as some parts of the DJL modules
     * are not managed by the Java GC.
     */
    @PreDestroy
    public void cleanup() {
        if (model != null) {
            model.close();
        }
    }

    @Override
    @Cacheable(cacheNames = "inference")
    public float predict(Inference inputData) throws TranslateException {
        try (Predictor<FNNModelInference, Float> predictor = model.newPredictor()) {
            return predictor.predict((FNNModelInference) inputData);
        }
    }

    /***
     * This Translator is needed to convert an input which is a Java float[] to an NDArray, so that the model can
     * work with it. Additionally, it performs a z-score normalization.
     */
    private static class FNNTranslator implements Translator<FNNModelInference, Float> {

        private  final float[] MEAN = {8.7132e+00f, 2.8966e+04f, 2.4517e+01f};
        private  final float[] STD = {7.3103e+00f, 1.0314e+04f, 2.8042e+01f};

        @Override
        public NDList processInput(TranslatorContext ctx, FNNModelInference input) {
            NDManager manager = ctx.getNDManager();
            float[] floatsInput = fnnModelPromptToFLoatArray(input);
            NDArray inputArray = manager.create(floatsInput);
            NDArray meanArray = manager.create(MEAN);
            NDArray stdArray = manager.create(STD);

            NDArray normalized = inputArray.sub(meanArray).div(stdArray);
            return new NDList(normalized);
        }

        /**
         * This method handles the output of the model. It applies the softmax
         * (<a href="https://en.wikipedia.org/wiki/Softmax_function">Softmax in Wikipedia</a>) activation function of,
         * the last value int the input List.
         *
         * @param ctx  the toolkit used for post-processing
         * @param list the output NDList after inference, usually immutable in engines like
         *             PyTorch. @see <a href="https://github.com/deepjavalibrary/djl/issues/1774">Issue 1774</a>
         * @return Confidence of the model that a damage (class 1) happens. Value between 0 and 1.
         */
        @Override
        public Float processOutput(TranslatorContext ctx, NDList list) {
            NDArray logits = list.singletonOrThrow();
            NDArray probs = logits.softmax(-1);
            float probClass1 = probs.getFloat(1);
            return  probClass1;
        }

        private float[] fnnModelPromptToFLoatArray(FNNModelInference fnnModelInference){
            float[] arr = new float[3];
            arr[0] = fnnModelInference.temperature_mean();
            arr[1] = fnnModelInference.sun_mean();
            arr[2] = fnnModelInference.rain_sum();
            return arr;
        }

    }
}