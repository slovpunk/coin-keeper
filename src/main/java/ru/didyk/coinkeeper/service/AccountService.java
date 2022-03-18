package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.form.AccountForm;
import ru.didyk.coinkeeper.model.Account;

public interface AccountService {

    //добавить счёт
    void save(Account form);

    Account getById(Long id);

    //добавлять траты
    void plusMinusBalance(Long id, Account account, Integer sum);
}
