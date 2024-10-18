package com.example.weatherbot.config;

import com.example.weatherbot.bot.WeatherBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class WeatherBotConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public TelegramBotsApi telegramBotsApi(WeatherBot weatherBot) throws TelegramApiException{
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(weatherBot);
        return api;
    }
}
