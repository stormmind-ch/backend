package com.stormmind.infrastructure.ai;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.springframework.stereotype.Service;

import java.io.IOException;

// TODO
@Service
public class LSTMModelInfereneceService implements ModelInferenceService {

    @Override
    public float[] predict(float[] inputData) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        return new float[0];
    }
}
