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

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findAccountById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.getById(id);
        accountRepository.delete(account);
    }
}
