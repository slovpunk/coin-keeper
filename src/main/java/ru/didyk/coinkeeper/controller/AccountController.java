package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.dto.AccountDTO;
import ru.didyk.coinkeeper.model.UserCategory;
import ru.didyk.coinkeeper.service.account.UserCategoryService;

@RestController
@RequestMapping(AccountController.PATH)
public class AccountController {

    public final static String PATH = "/api/coinkeeper/";

    private UserCategoryService userCategoryService;

    @Autowired
    public AccountController(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    @GetMapping("{account-id}")
    public UserCategory getAccount(@PathVariable(value = "account-id") Long id) {
        return userCategoryService.findAccountById(id).get();
    }

    @PostMapping("account")
    public UserCategory addAccount(@RequestBody AccountDTO account) {
        return userCategoryService.saveAccount(account);
    }

    @PutMapping("account")
    public UserCategory updateAccount(@RequestBody AccountDTO account) {
        return userCategoryService.saveAccount(account);
    }

    @DeleteMapping("delete/{account-id}")
    public void deleteAccount(@PathVariable(value = "account-id") Long id) {
        userCategoryService.deleteAccount(id);
    }
}
