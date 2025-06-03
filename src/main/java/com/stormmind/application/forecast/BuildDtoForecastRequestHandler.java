package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.domain.Forecast;
import com.stormmind.presentation.dtos.response.forecast.ForecastDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(40)
public class BuildDtoForecastRequestHandler extends  AbstractForecastHandler{

    @Override
    protected void doHandle(ForecastRequest forecastRequest) throws Exception {
        Forecast forecast = forecastRequest.getForecast();
        forecastRequest.setForecast(forecast);
    }
}
