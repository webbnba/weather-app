package com.example.openweather.parser;

import com.example.openweather.model.WeatherData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherParserImpl implements WeatherParser {

    private final ObjectMapper objectMapper;

    @Override
    public WeatherData parse(String weatherAsString) {

        try {
            JsonNode rootNode = objectMapper.readTree(weatherAsString);

            double temp = rootNode.path("main").path("temp").asDouble();
            double feelsLike = rootNode.path("main").path("feels_like").asDouble();
            String description = rootNode.path("weather").get(0).path("description").asText();
            int humidity = rootNode.path("main").path("humidity").asInt();
            double windSpeed = rootNode.path("wind").path("speed").asDouble();

            return WeatherData.builder()
                    .temp(temp)
                    .feelsLike(feelsLike)
                    .description(description)
                    .humidity(humidity)
                    .windSpeed(windSpeed)
                    .build();
        } catch (Exception e) {
            log.error("json parsing error, json:{}", weatherAsString, e);
            throw new WeatherDataParsingException(e);
        }
    }
}
