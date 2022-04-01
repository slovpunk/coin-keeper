package ru.didyk.coinkeeper.telegram;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.didyk.coinkeeper.model.ProductCategory;
import ru.didyk.coinkeeper.repository.ProductCategoryRepository;
import ru.didyk.coinkeeper.service.productCategory.ProductCategoryService;
import ru.didyk.coinkeeper.telegram.entity.ProductCategoryBot;
import ru.didyk.coinkeeper.telegram.service.ProductCategoryServiceBot;

import java.util.*;

@Component
public class Bot extends TelegramLongPollingBot {

    private ProductCategoryServiceBot productCategoryServiceBot;

    @SneakyThrows
    public static void main(String[] args) {
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @Override
    public String getBotUsername() {
        return "@BudKeepBot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String[] param = callbackQuery.getData().split(":");
        String action = param[0];
        ProductCategoryBot productCategory = ProductCategoryBot.valueOf(param[1]);
        productCategoryServiceBot.setProductCategory(message.getChatId(), productCategory);
    }

    // TODO: получать категории не из enum, а из таблицы product_category
    @SneakyThrows
    private void handleMessage(Message message) {
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command =
                        message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/set_product_category":
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//                        List<ProductCategory> list = productCategoryRepository.findAll();
//                        for (ProductCategory productCategory : list) {
//                            System.out.println();
//                            buttons.add(
//                                    Arrays.asList(
//                                            InlineKeyboardButton.builder()
//                                                    .text(productCategory.getTitle())
//                                                    .callbackData("Product category:" + productCategory)
//                                                    .build()));
//                        }
//                        execute(
//                                SendMessage.builder()
//                                        .text("Please choose product category")
//                                        .chatId(message.getChatId().toString())
//                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
//                                        .build());
//                        return;
                        for (ProductCategoryBot productCategory : ProductCategoryBot.values()) {
                            System.out.println();
                            buttons.add(
                                    Arrays.asList(
                                            InlineKeyboardButton.builder()
                                                    .text(productCategory.name())
                                                    .callbackData("Product category:" + productCategory)
                                                    .build()));
                        }
                        execute(
                                SendMessage.builder()
                                        .text("Please choose product category")
                                        .chatId(message.getChatId().toString())
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                        .build());
                        return;
                    case "/set_name_and_balance":
                }
            }
        }
        if (message.hasText()) {
            String messageText = message.getText();
            Optional<Double> value = parseDouble(messageText);
            if (value.isPresent()) {
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Ok")
                                .build());
            }
        }
    }

    private Optional<Double> parseDouble(String messageText) {
        try {
            return Optional.of(Double.parseDouble(messageText));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}