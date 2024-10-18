package com.example.openweather.service;

import com.example.openweather.config.OpenWeatherConfig;
import com.example.openweather.model.WeatherData;
import com.example.openweather.parser.WeatherParser;
import com.example.openweather.requester.WeatherDataRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherDataService {

    private final WeatherDataRequester dataRequester;
    private final WeatherParser parser;
    private final OpenWeatherConfig config;
    private final Cache<String, WeatherData> weatherDataCache;

    public WeatherData getWeatherData(String city) {
        log.info("getWeatherData. city:{}", city);

        WeatherData data = weatherDataCache.get(city);
        if (data != null) {
            return data;
        }

        String requestUrl = String.format(config.getUrl(), city) + "&appid=" + config.getKey();
        try {
            String weatherDataJson = dataRequester.getWeatherDataAsJson(requestUrl);
            WeatherData weatherData = parser.parse(weatherDataJson);
            weatherDataCache.put(city, weatherData);
            return weatherData;
        } catch (Exception e) {
            log.error("Failed to get weather data for city: {}", city, e);
            throw new WeatherDataNotFoundException("Weather data not found for city: " + city);
        }
    }
}
