package com.example.weatherbot.service;

import com.example.weatherbot.domain.WeatherData;

public interface WeatherClientService {

    WeatherData getWeatherData(String city);
}
