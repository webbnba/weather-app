package com.example.weatherbot.bot.command;

import com.example.weatherbot.bot.WeatherBot;

public class StartCommand extends Command{

    public StartCommand(WeatherBot weatherBot) {
        super(weatherBot);
    }

    public void startCommand(Long chatId, String userName) {
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
}
