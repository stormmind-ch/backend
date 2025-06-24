package com.stormmind.infrastructure.ai;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import com.stormmind.domain.FNNModelInference;
import com.stormmind.domain.Inference;
import com.stormmind.domain.SeqModelInference;

/***
 * This Translator is needed to convert an input which is a Java float[] to an NDArray, so that the model can
 * work with it. Additionally, it performs a z-score normalization.
 */

public class ModelTranslator implements Translator<Inference, Float> {

    private final ClusterSize clusterSize;

    public ModelTranslator( ClusterSize clusterSize) {
        this.clusterSize = clusterSize;
    }

    @Override
    public NDList processInput(TranslatorContext ctx, Inference input) {
        NDManager manager = ctx.getNDManager();
        float[] floatsInput = inferenceToFloatArray(input);

        NDArray inputArray;
        NDArray normalized;

        NDArray meanArray = manager.create(clusterSize.getMean()); // (3,)
        NDArray stdArray = manager.create(clusterSize.getStd());   // (3,)

        if (input instanceof FNNModelInference) {
            // Shape: (3,)
            inputArray = manager.create(floatsInput); // already (3,)
            normalized = inputArray.sub(meanArray).div(stdArray);
        }  else if (input instanceof SeqModelInference) {
            inputArray = manager.create(floatsInput, new Shape(3, 3));

            NDArray mean2D = meanArray.reshape(1, 3);  // shape: (1, 3)
            NDArray std2D = stdArray.reshape(1, 3);    // shape: (1, 3)

            normalized = inputArray.sub(mean2D).div(std2D);  // inputArray shape: (3, 3)

            System.out.println("DJL inputArray shape: " + inputArray.getShape());
            System.out.println("DJL normalized shape: " + normalized.getShape());

        } else {
            throw new IllegalArgumentException("Unknown inference type: " + input.getClass());
        }

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
        return probs.getFloat(1);
    }

    private float[] inferenceToFloatArray(Inference inference) {
        if (inference instanceof FNNModelInference(float temperatureMean, float sunMean, float rainSum)) {
            return new float[]{
                    temperatureMean,
                    sunMean,
                    rainSum
            };
        } else if (inference instanceof SeqModelInference(
                float temperatureMean, float sunMean, float rainSum, float temperatureMean1, float sunMean1,
                float rainSum1, float temperatureMean2, float sunMean2, float rainSum2
        )) {
            return new float[]{
                    temperatureMean,
                    sunMean,
                    rainSum,
                    temperatureMean1,
                    sunMean1,
                    rainSum1,
                    temperatureMean2,
                    sunMean2,
                    rainSum2
            };
        } else {
            throw new IllegalArgumentException("Unknown inference type: " + inference.getClass());
        }
    }
}