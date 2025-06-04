package com.stormmind.infrastructure.ai;

import ai.djl.repository.zoo.ModelNotFoundException;
import com.stormmind.application.ai.ModelInferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ModelInferenceServiceFactoryTest {

    private FNNModelInferenceService fnnService;
    private LSTMModelInfereneceService lstmService;
    private ModelInferenceServiceFactory factory;

    @BeforeEach
    void setUp() {
        fnnService = mock(FNNModelInferenceService.class);
        lstmService = mock(LSTMModelInfereneceService.class);
        factory = new ModelInferenceServiceFactory(fnnService, lstmService);
    }

    @Test
    void testGetModelInferenceService_returnsFnnService() throws Exception {
        ModelInferenceService result = factory.getModelInferenceService("FNN");
        assertSame(fnnService, result);
    }

    @Test
    void testGetModelInferenceService_returnsLstmService() throws Exception {
        ModelInferenceService result = factory.getModelInferenceService("LSTM");
        assertSame(lstmService, result);
    }

    @Test
    void testGetModelInferenceService_unknownModel_throwsException() {
        String unknownModel = "XYZ";

        Exception exception = assertThrows(ModelNotFoundException.class, () ->
                factory.getModelInferenceService(unknownModel));

        assertTrue(exception.getMessage().contains("No model found with name"));
    }
}