package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.form.AccountForm;
import ru.didyk.coinkeeper.model.Account;

import java.util.Optional;

public interface AccountService {

    Account save(Account account);

    Optional<Account> findById(Long aLong);

    void delete(Long id);

//    void plusMinusBalance(Long id, Account account, Integer sum);
}
