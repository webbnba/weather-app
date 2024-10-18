package com.example.weatherbot.service;

public interface UserSettingsService {

    String getUserDefaultCity(String userId);
    void setUserDefaultCity(String userId, String city);
}
