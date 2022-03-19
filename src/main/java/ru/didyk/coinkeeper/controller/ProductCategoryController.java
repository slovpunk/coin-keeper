package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.model.ProductCategory;
import ru.didyk.coinkeeper.service.AccountService;
import ru.didyk.coinkeeper.service.ProductCategoryService;


@RestController
@RequestMapping(AccountController.PATH)
public class ProductCategoryController {

    private ProductCategoryService service;
    private AccountService accountService;

    @Autowired
    public ProductCategoryController(ProductCategoryService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping("category/{id}")
    public ProductCategory getById(Long id) {
        return service.getById(id);
    }

    @PostMapping("add/{account-id}")
    public void addProductCategory(@RequestBody ProductCategory category, @PathVariable(name = "account-id") Long accountId) {
        service.addProductCategory(category, accountId);
    }

}
