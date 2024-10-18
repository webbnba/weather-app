package com.example.openweather.controller;

import com.example.openweather.model.WeatherData;
import com.example.openweather.service.WeatherDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    @GetMapping("/weatherData/{city}")
    public WeatherData getWeatherData(@PathVariable("city") String city) {
        log.info("getWeatherData, city:{}", city);
        WeatherData weatherData = weatherDataService.getWeatherData(city);
        log.info("weatherData:{}", weatherData);
        return weatherData;
    }
}
