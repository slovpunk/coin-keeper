package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.service.account.AccountService;

@RestController
@RequestMapping(AccountController.PATH)
public class AccountController {

    public final static String PATH = "/api/coinkeeper/";

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{account-id}")
    public Account getAccount(@PathVariable(value = "account-id") Long id) {
        return accountService.findAccountById(id).get();
    }

    @PostMapping("account")
    public Account addAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @PutMapping("account")
    public Account updateAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @DeleteMapping("delete/{account-id}")
    public void deleteAccount(@PathVariable(value = "account-id") Long id) {
        accountService.deleteAccount(id);
    }
}
