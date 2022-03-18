package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.service.AccountService;

@RestController
@RequestMapping("/api/coinkeeper/")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        System.out.println("Constructor");
        this.accountService = accountService;
    }

    @GetMapping("{id}")
    public Account getAccount(@PathVariable(value = "id") Long id) {
        System.out.println("Controller");
        return accountService.getById(id);
    }
}
