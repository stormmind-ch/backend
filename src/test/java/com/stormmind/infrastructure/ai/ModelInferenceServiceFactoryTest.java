package com.stormmind.infrastructure.ai;

import ai.djl.repository.zoo.ModelNotFoundException;
import com.stormmind.application.ai.ModelInferenceService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelInferenceServiceFactoryTest {

    private final ModelInferenceServiceFactory factory = new ModelInferenceServiceFactory();

    @Test
    void testGetModelInferenceService_returnsFnnService() throws Exception {
        ModelInferenceService result = factory.getModelInferenceService("FNN", ClusterSize.THREE);
        assertNotNull(result);
        assertTrue(result instanceof FNNModelInferenceService);
    }

    @Test
    void testGetModelInferenceService_returnsLstmService() throws Exception {
        ModelInferenceService result = factory.getModelInferenceService("LSTM", ClusterSize.SIX);
        assertNotNull(result);
        assertTrue(result instanceof LSTMModelInferenceService);
    }

    @Test
    void testGetModelInferenceService_returnsTransformerService() throws Exception {
        ModelInferenceService result = factory.getModelInferenceService("TRANSFORMER", ClusterSize.THREE);
        assertNotNull(result);
        assertTrue(result instanceof TransformerModelInferenceService);
    }

    @Test
    void testGetModelInferenceService_unknownModel_throwsException() {
        String unknownModel = "XYZ";

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                factory.getModelInferenceService(unknownModel, ClusterSize.SIX));

        assertInstanceOf(ModelNotFoundException.class, ex.getCause());
        assertTrue(ex.getCause().getMessage().contains("No model found with name"));
    }

}
