package com.stormmind.infrastructure.ai;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.domain.Inference;

import java.io.IOException;

public interface ModelInferenceService {
    float predict(Inference inputData) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException;
}
