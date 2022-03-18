package ru.didyk.coinkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.repository.AccountRepository;

import java.util.Optional;

@Component
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.getById(id);
        accountRepository.delete(account);
    }

//    @Override
//    public Account getById(Long id) {
//        System.out.println("Service");
//        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        Account account1 = accountRepository.getById(id);
//        return account1;
//    }

//    @Override
//    public void plusMinusBalance(Long id, Account account, Integer sum) {
//        Account account1 = accountRepository.getById(id);
//        account1.setBalance(account.getBalance() - sum);
//        accountRepository.save(account1);
//    }
}
