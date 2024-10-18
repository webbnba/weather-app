package com.example.openweather.controller;

import com.example.openweather.service.WeatherDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WeatherDataNotFoundException.class)
    public ResponseEntity<?> handleWeatherDataNotFoundException(WeatherDataNotFoundException ex, WebRequest request) {

        String errorMessage = ex.getMessage();

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
