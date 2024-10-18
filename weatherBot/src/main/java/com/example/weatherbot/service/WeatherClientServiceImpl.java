package com.example.weatherbot.service;

import com.example.weatherbot.domain.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherClientServiceImpl implements WeatherClientService {

    private final RestTemplate restTemplate;

    @Value("${open-weather-client.url}")
    private String weatherApiUrl;

    @Override
    public WeatherData getWeatherData(String city) {

        String url = String.format(weatherApiUrl, city);

        WeatherData data = restTemplate.getForObject(url, WeatherData.class);
        return data;
    }
}
