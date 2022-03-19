package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.service.AccountService;

@RestController
@RequestMapping(AccountController.PATH)
public class AccountController {

    public final static String PATH = "/api/coinkeeper/";

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{id}")
    public Account getAccount(@PathVariable(value = "id") Long id) {
        return accountService.findAccountById(id).get();
    }

    @PostMapping("/")
    public Account addAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @PutMapping("/")
    public Account updateAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAccount(@PathVariable(value = "id") Long id) {
        accountService.deleteAccount(id);
    }
}
