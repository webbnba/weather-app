package com.example.weatherbot.service;

import com.example.weatherbot.entity.UserSettings;
import com.example.weatherbot.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingServiceImpl implements UserSettingsService {

    private final UserSettingsRepository userSettingsRepository;

    public String getUserDefaultCity(String userId) {
        return userSettingsRepository.findById(userId)
                .map(UserSettings::getDefaultCity)
                .orElse(null);
    }

    public void setUserDefaultCity(String userId, String city) {
        UserSettings settings = userSettingsRepository.findById(userId)
                .orElse(new UserSettings());
        settings.setUserId(userId);
        settings.setDefaultCity(city);
        userSettingsRepository.save(settings);
    }
}
