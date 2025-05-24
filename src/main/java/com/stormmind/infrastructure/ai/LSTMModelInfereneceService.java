package com.stormmind.infrastructure.ai;

import com.stormmind.domain.Inference;
import org.springframework.stereotype.Service;

// TODO, cannot be done until we don't have a working LSTM
@Service
public class LSTMModelInfereneceService implements ModelInferenceService {

    @Override
    public float predict(Inference inputData) {
        return (float) 0;
    }
}
