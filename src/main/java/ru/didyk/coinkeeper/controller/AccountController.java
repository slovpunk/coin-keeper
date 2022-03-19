package ru.didyk.coinkeeper.controller;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        return accountService.findById(id).get();
    }

    @PostMapping("/")
    public Account add(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PutMapping("/")
    public Account update(@RequestBody Account account) {
        return accountService.save(account);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        accountService.delete(id);
    }
}
