package ru.didyk.coinkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /*
    При помощи этого метода пользователь добавляет счёт (Account) или обновляет с новыми данными
     */
    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    /*
    При помощи этого метода пользователь получает данные о счёте (Account) по id
     */
    @Override
    public Optional<Account> findAccountById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    /*
    При помощи этого метода пользователь может удалить счёт (Account)
     */
    @Override
    public void deleteAccount(Long id) {
        accountRepository.delete(accountRepository.getById(id));
    }
}
