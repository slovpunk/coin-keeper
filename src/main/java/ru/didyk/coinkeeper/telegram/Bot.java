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
import java.util.*;

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
        return "5171723480:AAFYKRQpKobxptC4e9pmPC6X9vjaQUo6t14";
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
                        return;
                    case "/set_user":
                        execute(
                                SendMessage.builder()
                                        .text("Please enter your name")
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        return;
                    case "/set_category":
                        execute(
                                SendMessage.builder()
                                        .text("Please enter category name with \"+\". For ex: Food+ ")
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        return;
                    case "/get_balance":
                        // TODO: добавить исключение на случай пустой таблицы
                        List<UserCategory> userCategories = userCategoryService.getAll();
                        List<MoneyMovement> moneyMovements = moneyMovementService.getAllMoneyMovement();
                        StringBuilder builderForBalance = new StringBuilder();
                        if (userCategories.isEmpty()) {
                            execute(
                                    SendMessage.builder()
                                            .text("You have not categories")
                                            .chatId(message.getChatId().toString())
                                            .build()
                            );
                            return;
                        }
                        for (UserCategory userCategory : userCategories) {
                            Double values = moneyMovements.stream()
                                    .filter(m -> m.getCategory().getId().equals(userCategory.getId()))
                                    .map(MoneyMovement::getValue)
                                    .reduce((double) 0, Double::sum);
                            builderForBalance.append(userCategory.getName())
                                    .append(" : ")
                                    .append(values)
                                    .append("\n");
                        }
                        execute(
                                SendMessage.builder()
                                        .text(String.valueOf(builderForBalance))
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        return;
                }
            }
        }
        if (message.hasText()) {
            String messageText = message.getText();
            Long id = message.getFrom().getId();
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
            //TODO: убрать необходимость вводить "+"
            if (userName.isPresent() && !userCategoryName.get().contains("+")) {
                createUser(userName.get(), id);
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("User is added")
                                .build());
                return;
            }

            if (userCategoryName.isPresent() && userCategoryName.get().contains("+")) {
                createUserCategory(userCategoryName.get().replace("+", ""), id);
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("UserCategory is added")
                                .build());
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
        //TODO: вычитание из баланса суммы покупки
        Optional<UserCategory> userCategoryOptional = userCategoryService.findByName(userCategory.getName());
        List<UserCategory> categories = userCategoryService.getAll();
        //TODO: проблема в регистре имени категории
        if (categories.contains(userCategoryOptional.get()) && !userCategoryOptional.get().getName().equals("Balance")) {
            Optional<UserCategory> userCategory = userCategoryService.findByName("Balance");
            MoneyMovement moneyMovement1 = MoneyMovement.builder()
                    .category(userCategory.get())
                    .value(0 - value)
                    .occurredAt(LocalDateTime.now())
                    .build();
            moneyMovementService.addMoneyMovement(moneyMovement1);
        }
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

        UserCategory userCategory = UserCategory.builder()
                .name(name)
                .spending(true)
                .user(userService.getUserById(id))
                .build();
        if (name.equalsIgnoreCase("balance")) {
            userCategory.setSpending(false);
        }
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