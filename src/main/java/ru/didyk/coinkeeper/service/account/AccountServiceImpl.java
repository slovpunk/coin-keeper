package ru.didyk.coinkeeper.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.exception.AccountIsNull;
import ru.didyk.coinkeeper.exception.AccountNotFoundException;
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
        if(account.getId() == null) {
            throw new AccountIsNull("Account is null");
        }
        return accountRepository.save(account);
    }

    /*
    При помощи этого метода пользователь получает данные о счёте (Account) по id
     */
    @Override
    public Optional<Account> findAccountById(Long aLong) {
        if(accountRepository.findById(aLong).isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }
        return accountRepository.findById(aLong);
    }

    /*
    При помощи этого метода пользователь может удалить счёт (Account)
    TODO: создать исключение для этого метода
     */
    @Override
    public void deleteAccount(Long id) {
        accountRepository.delete(accountRepository.getById(id));
    }
}
