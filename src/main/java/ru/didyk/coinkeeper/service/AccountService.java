package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.model.Account;

import java.util.Optional;

public interface AccountService {

    Account saveAccount(Account account);

    Optional<Account> findAccountById(Long aLong);

    void deleteAccount(Long id);

//    void plusMinusBalance(Long id, Account account, Integer sum);
}
