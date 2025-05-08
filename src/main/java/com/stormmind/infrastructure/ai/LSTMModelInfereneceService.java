package com.stormmind.infrastructure.ai;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.domain.AIPrompt;
import org.springframework.stereotype.Service;

import java.io.IOException;

// TODO, cannot be done until we don't have a working LSTM
@Service
public class LSTMModelInfereneceService implements ModelInferenceService {

    @Override
    public float predict(AIPrompt inputData) {
        return (float) 0;
    }
}
