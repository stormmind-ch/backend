package com.stormmind.infrastructure.weather_api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.stormmind.domain.Duration;
import com.stormmind.domain.Municipality;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AbstractOpenMeteoFetcher{
    @Value("${url.scheme}")
    String scheme;
    @Value("${url.forecastHost}")
    String forecastHost;
    @Value("${url.archivHost}")
    String archivHost;
    @Value("${url.forecastPath}")
    String forecastPath;
    @Value("${url.archivPath}")
    String archivPath;
    @Value("${api.key}")
    String apiKey;

    public WeatherValueDTO fetchData(URL url){
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int response = conn.getResponseCode();

            if (response != 200) {
                throw new RuntimeException("HttpResponseCode: " + response);
            } else {
                InputStreamReader reader = new InputStreamReader(url.openStream());

                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                List<Double> temperature_list = new Gson().fromJson(
                        jsonObject.getAsJsonArray("soil_temperature_0_to_100cm_mean"), new TypeToken<List<Double>>() {}.getType()
                );
                List<Double> rain_list = new Gson().fromJson(
                        jsonObject.getAsJsonArray("rain_sum"), new TypeToken<List<Double>>() {}.getType()
                );
                List<Double> snow_list = new Gson().fromJson(
                        jsonObject.getAsJsonArray("snowfall_sum"), new TypeToken<List<Double>>() {}.getType()
                );
                List<Double> sunshine_list = new Gson().fromJson(
                        jsonObject.getAsJsonArray("sunshine_duration"), new TypeToken<List<Double>>() {}.getType()
                );

                return new WeatherValueDTO(
                        temperature_list,//temperature
                        rain_list,//rain
                        snow_list,//snow
                        sunshine_list//sunshine
                        );
            }
        } catch (IOException e) {}
        return null;
    }

    /**
     *
     * @param offsetInWeeks - 0 returns today until today + 7 days
     * @return
     */
    Duration getWeek(Integer offsetInWeeks){
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(offsetInWeeks * 7L);
        LocalDate end = start.plusDays(7L);
        String startString = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endString = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return new Duration(startString, endString);
    }
    

    URL buildUrl(Municipality centroidMunicipal, Integer offsetInWeeks) {
        /**
         * since the historical weather data api has a five-day delay we use weather forecast for previous and next week
         * and historical weather data otherwise.
         */
        Boolean useArchiveUrl = offsetInWeeks > 2;
        Duration week = this.getWeek(offsetInWeeks);
        try {
            URIBuilder uriBuilder = getUriBuilder(centroidMunicipal, useArchiveUrl, week);
            return uriBuilder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private URIBuilder getUriBuilder(Municipality centroidMunicipal, Boolean useArchiveUrl, Duration week) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(useArchiveUrl ? archivHost : forecastHost);
        uriBuilder.setPath(useArchiveUrl ? archivPath : forecastPath);
        uriBuilder.setParameter("lat", String.valueOf(centroidMunicipal.coordinates().latitude()));
        uriBuilder.setParameter("lon", String.valueOf(centroidMunicipal.coordinates().longitude()));
        uriBuilder.setParameter("start", week.start());
        uriBuilder.setParameter("end", week.end());
        uriBuilder.setParameter("apikey", apiKey);
        return uriBuilder;
    }
}
