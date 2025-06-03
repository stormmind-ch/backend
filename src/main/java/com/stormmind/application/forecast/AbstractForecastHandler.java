package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;

public abstract class AbstractForecastHandler implements ForecastRequestHandler {

    private ForecastRequestHandler next;

    @Override
    public void setNext(ForecastRequestHandler forecastRequestHandler) {
        next = forecastRequestHandler;
    }

    @Override
    public void handle(ForecastRequest forecastRequest) throws  Exception{
        doHandle(forecastRequest);
        if (next != null && forecastRequest.getForecast() == null) {
            next.handle(forecastRequest);
        }
    }

    protected abstract void  doHandle(ForecastRequest forecastRequest) throws Exception;
}
