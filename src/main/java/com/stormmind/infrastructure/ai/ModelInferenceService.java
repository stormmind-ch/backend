package com.stormmind.infrastructure.ai;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;

import java.io.IOException;

public interface ModelInferenceService {
    float[] predict(float[] inputData) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException;
}
