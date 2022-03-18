package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.form.AccountForm;
import ru.didyk.coinkeeper.model.Account;

import java.util.Optional;

public interface AccountService {

    void save(Account form);

    Optional<Account> findById(Long aLong);

//    void plusMinusBalance(Long id, Account account, Integer sum);
}
