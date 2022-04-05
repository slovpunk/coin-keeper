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
    public UserCategory userCategorySelectedByButton = new UserCategory();

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
        userCategorySelectedByButton = userCategoryService.findAccountById(Long.valueOf(param[1])).get();
    }

    // метод, которые помогает понять существует ли юзер. Метод используется, чтобы заблокировать
    // другие команды бота, если юзер не создан
    private boolean getUsers() {
        return userService.getAllUsers().isEmpty();
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
                    // зафиксировать транзакцию по выбранной категории пользователя
                    case "/set_money_movement" -> {
                        if (getUsers()) {
                            execute(
                                    SendMessage.builder()
                                            .text("Please add new user by command /set_user")
                                            .chatId(message.getChatId().toString())
                                            .build());
                            return;
                        }
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
                    }
                    // присвоить имя для пользователя
                    case "/set_user" -> {
                        execute(
                                SendMessage.builder()
                                        .text("Please enter your name")
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        return;
                    }
                    // создать новую категорию
                    case "/set_category" -> {
                        if (getUsers()) {
                            execute(
                                    SendMessage.builder()
                                            .text("Please add new user by command /set_user")
                                            .chatId(message.getChatId().toString())
                                            .build());
                            return;
                        }
                        execute(
                                SendMessage.builder()
                                        .text("Please enter category name with \"+\". For ex: Food+ ")
                                        .chatId(message.getChatId().toString())
                                        .build()
                        );
                        return;
                    }
                    // получить баланс на основе всех сохраненных транзакций и вывести по категориям
                    case "/get_balance" -> {
                        if (getUsers()) {
                            execute(
                                    SendMessage.builder()
                                            .text("Please add new user by command /set_user")
                                            .chatId(message.getChatId().toString())
                                            .build());
                            return;
                        }
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
        }
        if (message.hasText()) {
            String messageText = message.getText();
            //получить id для пользователя из Телеграма
            Long id = message.getFrom().getId();
            Optional<Double> value = parseDouble(messageText);
            Optional<String> userName = parseString(messageText);
            Optional<String> userCategoryName = parseString(messageText);

            // условие, которое обрабатывает ввод цифр
            if (value.isPresent()) {
                buildMoneyMovement(value.get());
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Movement is added")
                                .build());
                return;
            }
            //условие, которое обрабатывает ввод имени пользователя
            if (userName.isPresent() && !userCategoryName.get().contains("+")) {
                createUser(userName.get(), id);
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("User is added")
                                .build());
                return;
            }
            //TODO: убрать необходимость вводить "+"
            //условие, которое обрабатывает ввод категории с "+"
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
    private void buildMoneyMovement(Double enteredExpenseAmount) {
        MoneyMovement newMoneyMovement = MoneyMovement.builder()
                .category(userCategorySelectedByButton) //проблема в том, что userCategory является глобальной переменной
                .value(enteredExpenseAmount)
                .occurredAt(LocalDateTime.now())
                .build();
        //если это баланс, то мы не заходим в это условие. Сравнение идет по выбранной по кнопке категории
        // и категории, имеющей false в spending
        if (userCategoryService.getBySpendingFalse().isPresent() && !userCategorySelectedByButton.getName().equals(userCategoryService.getBySpendingFalse().get().getName())) {
            UserCategory balanceCategory = userCategoryService.getBySpendingFalse().get();
            MoneyMovement moneyMovementForBalanceCategory = MoneyMovement.builder()
                    .category(balanceCategory)
                    .value(0 - enteredExpenseAmount)
                    .occurredAt(LocalDateTime.now())
                    .build();
            moneyMovementService.addMoneyMovement(moneyMovementForBalanceCategory);
        }
        moneyMovementService.addMoneyMovement(newMoneyMovement);
    }

    // создаёт нового пользователя с id пользователя из Телеграм
    private void createUser(String name, Long id) {
        User newUser = User.builder()
                .id(id)
                .name(name)
                .build();
        userService.addUser(newUser);
    }

    // создаёт новую категорию
    private void createUserCategory(String name, Long id) {

        // условие, которое выполняется если категории Баланс не существует,
        // а пользователь хочет добавить другую категорию
        if (userCategoryService.getAll().isEmpty()) {
            //создаём новую категорию "Balance"
            UserCategory balanceCategory = UserCategory.builder()
                    .name("Balance")
                    .spending(false)
                    .user(userService.getUserById(id))
                    .build();
            userCategoryService.addUserCategory(balanceCategory);
            //создаём первую запись, фиксирующую баланс на 0
            MoneyMovement firstTransactionForBalanceCategory = MoneyMovement.builder()
                    .category(balanceCategory) //проблема в том, что userCategory является глобальной переменной
                    .value(0.0)
                    .occurredAt(LocalDateTime.now())
                    .build();
            moneyMovementService.addMoneyMovement(firstTransactionForBalanceCategory);
        }
        // создаётся категория
        UserCategory newUserCategory = UserCategory.builder()
                .name(name)
                .spending(true)
                .user(userService.getUserById(id))
                .build();
        userCategoryService.addUserCategory(newUserCategory);
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