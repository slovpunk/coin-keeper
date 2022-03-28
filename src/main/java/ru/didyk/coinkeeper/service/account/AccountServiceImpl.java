package ru.didyk.coinkeeper.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.dto.AccountDTO;
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
    public Account saveAccount(AccountDTO account) {
//        if(account.getId() == null) {
//            throw new AccountIsNull("Account is null");
//        }
        Account newAccount = Account.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .build();
        return accountRepository.save(newAccount);
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
     */
    @Override
    public void deleteAccount(Long id) {
        if(accountRepository.findById(id).isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }
        accountRepository.delete(accountRepository.getById(id));
    }
}
