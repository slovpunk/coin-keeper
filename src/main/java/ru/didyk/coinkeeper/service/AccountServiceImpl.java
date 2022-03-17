package ru.didyk.coinkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.form.AccountForm;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(AccountForm form) {
        Account account = Account.builder()
                .id(form.getId())
                .balance(form.getBalance())
                .build();
        accountRepository.save(account);
    }

    @Override
    public void plusMinusBalance(Long id, Account account, Integer sum) {
        Account account1 = accountRepository.getById(id);
        account1.setBalance(account.getBalance() - sum);
        accountRepository.save(account1);
    }
}
