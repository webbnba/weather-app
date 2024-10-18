package com.example.openweather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "weather-api")
public class OpenWeatherConfig {

    private String url;
    private String key;
}
