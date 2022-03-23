package ru.didyk.coinkeeper.service.account;

import ru.didyk.coinkeeper.dto.AccountDTO;
import ru.didyk.coinkeeper.model.Account;

import java.util.Optional;

public interface AccountService {

    Account saveAccount(AccountDTO account);

    Optional<Account> findAccountById(Long aLong);

    void deleteAccount(Long id);
}
