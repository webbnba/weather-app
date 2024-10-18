package com.example.openweather.parser;

import com.example.openweather.model.WeatherData;

public interface WeatherParser {

    WeatherData parse(String weatherAsString);
}
