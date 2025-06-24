package com.stormmind.infrastructure.ai;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import com.stormmind.domain.FNNModelInference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FNNModelInferenceServiceTest {

    private TransformerModelInferenceService inferenceService;
    private ZooModel<FNNModelInference, Float> mockModel;
    private Predictor<FNNModelInference, Float> mockPredictor;

    @BeforeEach
    void setup() throws ModelNotFoundException, MalformedModelException, IOException {
        inferenceService = new TransformerModelInferenceService(ClusterSize.SIX);

        mockModel = mock(ZooModel.class);
        mockPredictor = mock(Predictor.class);

        try {
            var modelField = TransformerModelInferenceService.class.getDeclaredField("model");
            modelField.setAccessible(true);
            modelField.set(inferenceService, mockModel);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock model into service", e);
        }
    }

    @Test
    void testPredictReturnsExpectedValue() throws Exception {
        FNNModelInference input = new FNNModelInference(10f, 25000f, 30f);
        float expectedPrediction = 0.85f;

        when(mockModel.newPredictor()).thenReturn(mockPredictor);
        when(mockPredictor.predict(input)).thenReturn(expectedPrediction);

        float prediction = inferenceService.predict(input);

        assertEquals(expectedPrediction, prediction, 1e-5);
        verify(mockModel).newPredictor();
        verify(mockPredictor).predict(input);
        verify(mockPredictor).close();
    }

    @Test
    void testCleanupClosesModel() {
        doNothing().when(mockModel).close();

        inferenceService.cleanup();

        verify(mockModel).close();
    }
}