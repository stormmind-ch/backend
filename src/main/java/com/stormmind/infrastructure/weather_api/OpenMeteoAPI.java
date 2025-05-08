package com.stormmind.infrastructure.weather_api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenMeteoAPI {
    /*
    *https://archive-api.open-meteo.com/v1/archive?latitude=52.52&longitude=13.41
    * &start_date=2025-04-22&end_date=2025-05-06&daily=soil_temperature_0_to_100cm_mean,
    * sunshine_duration,rain_sum,snowfall_sum&hourly=temperature_2m&apikey=$API-KEY
    *
    *
    * https://customer-api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m&apikey=$API-KEY
    *
    * https://customer-archive-api.open-meteo.com/v1/archive?latitude=52.52&longitude=13.41&start_date=2025-04-22&end_date=2025-05-06&hourly=temperature_2m&apikey=$API-KEY
    */

/*
    private String scheme = "https";
    private String forecastHost = "customer-api.open-meteo.com";
    private String archivHost = "customer-archive-api.open-meteo.com";
    private String forecastPath = "/v1/forecast";
    private String archivPath = "/v1/archive";
    @Value("${api.key}")
    private String apiKey;

    @PostConstruct
    public void init(){}

    public OpenMeteoAPI() {}

    private URL buildForecastUrl(String lat, String lon,
                     String start, String end) throws MalformedURLException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(forecastHost);
        uriBuilder.setPath(forecastPath);
        uriBuilder.setParameter("lat", lat);
        uriBuilder.setParameter("lon", lon);
        uriBuilder.setParameter("start", start);
        uriBuilder.setParameter("end", end);
        uriBuilder.setParameter("apikey", apiKey);
        return uriBuilder.build().toURL();
    }

    private URL buildArchivUrl(String lat, String lon,
                     String start, String end) throws MalformedURLException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(archivHost);
        uriBuilder.setPath(archivPath);
        uriBuilder.setParameter("lat", lat);
        uriBuilder.setParameter("lon", lon);
        uriBuilder.setParameter("start", start);
        uriBuilder.setParameter("end", end);
        uriBuilder.setParameter("apikey", apiKey);
        return uriBuilder.build().toURL();
    }

    public WeatherDataDTO fetchData(String municipal, String centroidMunicipal,String lat, String lon,
                                    String start, String end) {
        try {
            URL url = buildUrl(lat,lon,start,end);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                InputStreamReader reader = new InputStreamReader(url.openStream());

                // JSON parsen
                JsonObject joson_obj = JsonParser.parseReader(reader).getAsJsonObject();

                List<Double> temperature_list = new Gson().fromJson(
                        joson_obj.getAsJsonArray("soil_temperature_0_to_100cm_mean"), new TypeToken<List<Double>>() {}.getType()
                );
                List<Double> rain_list = new Gson().fromJson(
                        joson_obj.getAsJsonArray("rain_sum"), new TypeToken<List<Double>>() {}.getType()
                );
                List<Double> snow_list = new Gson().fromJson(
                        joson_obj.getAsJsonArray("snowfall_sum"), new TypeToken<List<Double>>() {}.getType()
                );
                List<Double> sunshine_list = new Gson().fromJson(
                        joson_obj.getAsJsonArray("sunshine_duration"), new TypeToken<List<Double>>() {}.getType()
                );

                return new WeatherDataDTO(
                        municipal,//municipal
                        centroidMunicipal,//centroidMunicipal
                        Collections.emptyList(),
                        temperature_list,//temperature
                        rain_list,//rain
                        snow_list,//snow
                        sunshine_list//sunshine
                        );
            }
        } catch (IOException e) {} catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return null;
    }*/

}
