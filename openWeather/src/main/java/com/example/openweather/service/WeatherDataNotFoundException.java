package com.example.openweather.service;

public class WeatherDataNotFoundException extends RuntimeException{

    public WeatherDataNotFoundException(String message) { super(message);}
}
