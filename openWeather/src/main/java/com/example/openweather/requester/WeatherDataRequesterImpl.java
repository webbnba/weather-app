package com.example.openweather.requester;

import com.example.openweather.config.OpenWeatherConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherDataRequesterImpl implements WeatherDataRequester {

    @Override
    public String getWeatherDataAsJson(String url) {
        try {
            log.info("request for url:{}", url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("OpenWeather request error, url:{}", url);
            throw new RequesterException(e);
        }
    }
}
