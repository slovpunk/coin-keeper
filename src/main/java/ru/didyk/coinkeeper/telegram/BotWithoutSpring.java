//package ru.didyk.coinkeeper.telegram;
//
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.MessageEntity;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//import ru.didyk.coinkeeper.model.MoneyMovement;
//import ru.didyk.coinkeeper.service.productCategory.MoneyMovementServiceImpl;
//import ru.didyk.coinkeeper.telegram.entity.ProductCategoryBot;
//import ru.didyk.coinkeeper.telegram.service.ProductCategoryServiceBot;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//public class BotWithoutSpring extends TelegramLongPollingBot {
//
//    private ProductCategoryServiceBot productCategoryServiceBot;
//
//    @SneakyThrows
//    public static void main(String[] args) {
//        BotWithoutSpring bot = new BotWithoutSpring();
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(bot);
//    }
//
//    @Override
//    public String getBotUsername() {
//        return "@BudKeepBot";
//    }
//
//    @Override
//    public String getBotToken() {
//        return "5171723480:AAFYKRQpKobxptC4e9pmPC6X9vjaQUo6t14";
//    }
//
//    @Override
//    @SneakyThrows
//    public void onUpdateReceived(Update update) {
//        if (update.hasCallbackQuery()) {
//            handleCallback(update.getCallbackQuery());
//        }
//        if (update.hasMessage()) {
//            handleMessage(update.getMessage());
//        }
//    }
//
//    @SneakyThrows
//    private void handleCallback(CallbackQuery callbackQuery) {
//        Message message = callbackQuery.getMessage();
//        String[] param = callbackQuery.getData().split(":");
//        String action = param[0];
//        ProductCategoryBot productCategory = ProductCategoryBot.valueOf(param[1]);
//        productCategoryServiceBot.setProductCategory(message.getChatId(), productCategory);
//    }
//
//    // TODO: ???????????????? ?????????????????? ???? ???? enum, ?? ???? ?????????????? product_category
//    @SneakyThrows
//    private void handleMessage(Message message) {
//        if (message.hasText() && message.hasEntities()) {
//            Optional<MessageEntity> commandEntity =
//                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
//            if (commandEntity.isPresent()) {
//                String command =
//                        message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
//                switch (command) {
//                    case "/set_product_category":
//                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//                        for (ProductCategoryBot productCategory : ProductCategoryBot.values()) {
//                            System.out.println("pc !!!!! " + productCategory);
//                            buttons.add(
//                                    Arrays.asList(
//                                            InlineKeyboardButton.builder()
//                                                    .text(productCategory.name())
//                                                    .callbackData("Product category:" + productCategory)
//                                                    .build()));
//                        }
//                        System.out.println("but !!!! " + buttons);
//                        execute(
//                                SendMessage.builder()
//                                        .text("Please choose product category")
//                                        .chatId(message.getChatId().toString())
//                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
//                                        .build());
//                        return;
//                    case "/set_name_and_balance":
//                }
//            }
//        }
//        if (message.hasText()) {
//            String messageText = message.getText();
//            Optional<Double> value = parseDouble(messageText);
//            if (value.isPresent()) {
//                execute(
//                        SendMessage.builder()
//                                .chatId(message.getChatId().toString())
//                                .text("Ok")
//                                .build());
//            }
//        }
//    }
//
//    private Optional<Double> parseDouble(String messageText) {
//        try {
//            return Optional.of(Double.parseDouble(messageText));
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//    }
//}