package com.example.weatherbot.bot;

import com.example.weatherbot.domain.WeatherData;
import com.example.weatherbot.exception.WeatherDataNotFoundException;
import com.example.weatherbot.service.RequestLogService;
import com.example.weatherbot.service.UserSettingsService;
import com.example.weatherbot.service.WeatherClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Instant;

@Component
@Slf4j
public class WeatherBot extends TelegramLongPollingBot {

    private static final String START = "/start";
    private static final String WEATHER = "/weather";
    private static final String SET_CITY = "/setcity";
    private static final String HELP = "/help";
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private WeatherClientService weatherClientService;
    @Autowired
    private RequestLogService requestLogService;

    public WeatherBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userId = update.getMessage().getFrom().getId().toString();

            String[] parts = messageText.split(" ");
            String command = parts[0];
            String city = parts.length > 1 ? parts[1] : null;

            switch (command) {
                case START -> {
                    String userName = update.getMessage().getChat().getUserName();
                    startCommand(chatId, userName);
                }
                case WEATHER -> {
                    if (city == null) {
                        city = userSettingsService.getUserDefaultCity(userId);
                        if (city == null) {
                            sendMessage(chatId, "Пожалуйста укажите город для выбора основного города /setcity <city>");
                            return;
                        }
                    }
                    try {
                        WeatherData weatherData = weatherClientService.getWeatherData(city);
                        String responseMessage = formatWeatherResponse(weatherData, city);
                        sendMessage(chatId, responseMessage);
                        requestLogService
                                .logRequest(userId, "/weather " + city, Instant.now(), responseMessage);
                    } catch (WeatherDataNotFoundException e) {
                        sendMessage(chatId, "Данные погоды не найдены для вашего города: " + city);
                    } catch (Exception e) {
                        log.error("Произошла неожиданная ошибка при получении данных о погоде: {}", e.getMessage());
                        sendMessage(chatId, "Произошла ошибка при получении данных о погоде. Пожалуйста, попробуйте позже.");
                    }
                }

                case SET_CITY -> {
                    if (city != null) {
                        userSettingsService.setUserDefaultCity(userId, city);
                        sendMessage(chatId, "Основной город: " + city);
                    } else {
                        sendMessage(chatId, "Пожалуйста, укажите город в формате: /setcity <city>");
                    }
                }

                case HELP -> {
                    sendMessage(chatId, "Используйте /weather <city> для получения текущей погоды.\n" +
                            "Используйте /setcity <city> для выбора основного города.");
                }

                default -> {
                    unknownCommand(chatId);
                }
            }
        }
    }

    private void startCommand(Long chatId, String userName) {
        var text = """
                    Добро пожаловать в бот, %s!
                Этот бот был создан в качестве теста для компании BobrAi.

                Вы можете узнать текущую погоду в любом городе.
                                
                Для этого воспользуйтесь командой:
                /weather <город> - получить информацию о погоде в указанном городе.

                Дополнительные команды:
                /setcity <город> - установить город по умолчанию для запросов погоды.
                /help - получить справку по использованию бота.
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }

    private void unknownCommand(Long chatId) {
        var text = "Не удалось распознать команду! Нажмите /help для выбора возможных команд.";
        sendMessage(chatId, text);
    }

    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    private String formatWeatherResponse(WeatherData weatherData, String city) {
        return String.format("Текущая погода в %s:\n" +
                        "Температура: %.1f°C\nОщущается как: %.1f°C\nОписание: %s\nВлажность: %d%%\nСкорость ветра: %.1f м/с",
                city, weatherData.getTemp(), weatherData.getFeelsLike(),
                weatherData.getDescription(), weatherData.getHumidity(), weatherData.getWindSpeed());
    }

    @Override
    public String getBotUsername() {
        return "BobrAiWeather_bot";
    }
}
