package com.example.openweather.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Value
@Builder
public class WeatherData {
    double temp;
    double feelsLike;
    String description;
    int humidity;
    double windSpeed;
}
