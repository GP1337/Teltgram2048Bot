import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {

    Integer lastMessageId;

    ReplyKeyboardMarkup replyKeyboardMarkup;

    Game game;
    String gameString;

    KeyboardFactory keyboardFactory;

    Properties properties = new Properties();

    public static LongPollingBot getBot() {

        return new Bot();
    }

    public Bot() {

        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();
        this.keyboardFactory = new KeyboardFactory();
        this.game = new Game();
        this.gameString = this.game.toString();

        try {

            InputStream inputStream = new FileInputStream("src/main/resources/config.properties");

            properties.load(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getBotUsername() {
        return properties.getProperty("bot.username");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("bot.token");
    }

    @Override
    public void onUpdateReceived(Update update) {

        String message = update.getMessage().getText();

        if (message.equals("/start")){

            replyKeyboardMarkup.setKeyboard(keyboardFactory.getKeyboard(message));

            SendMessage sendMessageInitKeyboard = new SendMessage();
            sendMessageInitKeyboard.enableMarkdown(true);
            sendMessageInitKeyboard.setChatId(update.getMessage().getChatId().toString());
            sendMessageInitKeyboard.setText("Starting...");
            sendMessageInitKeyboard.setReplyMarkup(replyKeyboardMarkup);

            try {

                execute(sendMessageInitKeyboard);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            this.game = new Game();

            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(game.toString());
            sendMessage.setParseMode("HTML");

            try {

                Message messageCB = execute(sendMessage);

                lastMessageId = messageCB.getMessageId();

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        else {

            if (message.equals("⬆")) {
                game.moveUp();
            } else if (message.equals("⬅")) {
                game.moveLeft();
            } else if (message.equals("➡")) {
                game.moveRight();
            } else if (message.equals("⬇")) {
                game.moveDown();
            }
            else return;

            String currentGameString = game.toString();

            if (!gameString.equals(currentGameString)) {

                gameString = currentGameString;

                EditMessageText sendMessage = new EditMessageText();
                sendMessage.enableMarkdown(true);
                sendMessage.setChatId(update.getMessage().getChatId().toString());

                sendMessage.setText(currentGameString);
                sendMessage.setMessageId(lastMessageId);
                sendMessage.setParseMode("HTML");

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (game.isGameOver()){}

            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(update.getMessage().getChatId().toString());
            deleteMessage.setMessageId(update.getMessage().getMessageId());

            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

}
