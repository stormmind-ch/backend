package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import org.springframework.stereotype.Component;

@Component
public interface ForecastRequestHandler {
    void setNext(ForecastRequestHandler forecastRequestHandler);
    void handle(ForecastRequest forecastRequest) throws Exception;
}
