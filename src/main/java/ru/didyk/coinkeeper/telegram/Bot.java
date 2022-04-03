package ru.didyk.coinkeeper.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.didyk.coinkeeper.model.MoneyMovement;
import ru.didyk.coinkeeper.model.User;
import ru.didyk.coinkeeper.model.UserCategory;
import ru.didyk.coinkeeper.service.account.UserCategoryService;
import ru.didyk.coinkeeper.service.productCategory.MoneyMovementService;
import ru.didyk.coinkeeper.service.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {

    private final UserCategoryService userCategoryService;
    private final MoneyMovementService moneyMovementService;
    private final UserService userService;
    public UserCategory userCategory = new UserCategory();

    @Autowired
    public Bot(TelegramBotsApi telegramBotsApi,
               UserCategoryService userCategoryService,
               MoneyMovementService moneyMovementService,
               UserService userService) throws TelegramApiException {
        this.userCategoryService = userCategoryService;
        this.moneyMovementService = moneyMovementService;
        this.userService = userService;

        telegramBotsApi.registerBot(this);
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
    @Transactional
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        String[] param = callbackQuery.getData().split(":");
        userCategory = userCategoryService.findAccountById(Long.valueOf(param[1])).get();
    }


    private void handleMessage(Message message) throws TelegramApiException {
        List<UserCategory> list;
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command =
                        message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/set_money_movement":
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        list = userCategoryService.getAll();
                        for (UserCategory userCategory : list) {
                            buttons.add(
                                    Arrays.asList(
                                            InlineKeyboardButton.builder()
                                                    .text(userCategory.getName())
                                                    .callbackData("Product category:" + userCategory.getId())
                                                    .build()));
                        }
                        execute(
                                SendMessage.builder()
                                        .text("Please choose product category")
                                        .chatId(message.getChatId().toString())
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                        .build());
                        System.out.println(message.getChatId().toString());
                        return;
                    case "/set_user":
                        execute(
                                SendMessage.builder()
                                        .text("Please enter your name")
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        System.out.println(message.getChatId().toString());
                        return;
                    case "/set_user_category":
                        execute(
                                SendMessage.builder()
                                        .text("Please enter category name.")
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        System.out.println(message.getChatId().toString());
                        return;
                }
            }
        }
        if (message.hasText()) {
            String messageText = message.getText();
            Long id = message.getChatId();
            Optional<Double> value = parseDouble(messageText);
            Optional<String> userName = parseString(messageText);
            Optional<String> userCategoryName = parseString(messageText);

            if (value.isPresent()) {
                buildMoneyMovement(value.get());
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Movement is added")
                                .build());
                return;
            }

            if (userCategoryName.isPresent() && userCategoryName.get().contains("!")) {
                System.out.println(userCategoryName.get().contains("!"));
                createUserCategory(userCategoryName.get(), id);
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("UserCategory is added")
                                .build());
                return;
            }

            if (userName.isPresent() && !userCategoryName.get().contains("!")) {
                createUser(userName.get(), id);
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("User is added")
                                .build());
                return;
            }
        }
    }

    //создаёт новую запись о движении денег
    private void buildMoneyMovement(Double value) {
        MoneyMovement moneyMovement = MoneyMovement.builder()
                .category(userCategory) //проблема в том, что userCategory является глобальной переменной
                .value(value)
                .occurredAt(LocalDateTime.now())
                .build();
        moneyMovementService.addMoneyMovement(moneyMovement);
    }

    private void createUser(String name, Long id) {
        User user = User.builder()
                .id(id)
                .name(name)
                .build();
        userService.addUser(user);
    }

    private void createUserCategory(String name, Long id) {
        name = name.replace("!", "");
        UserCategory userCategory = UserCategory.builder()
                .name(name)
                .spending(true)
                .user(userService.getUserById(id))
                .build();
        userCategoryService.addUserCategory(userCategory);
    }

    private Optional<Double> parseDouble(String messageText) {
        try {
            return Optional.of(Double.parseDouble(messageText));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<String> parseString(String messageText) {
        try {
            return Optional.of(String.valueOf(messageText));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}