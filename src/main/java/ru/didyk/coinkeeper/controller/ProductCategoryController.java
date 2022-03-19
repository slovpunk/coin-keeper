package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.ProductCategory;
import ru.didyk.coinkeeper.service.AccountService;
import ru.didyk.coinkeeper.service.ProductCategoryService;


@RestController
@RequestMapping(AccountController.PATH)
public class ProductCategoryController {

    private ProductCategoryService service;

    @Autowired
    public ProductCategoryController(ProductCategoryService service) {
        this.service = service;
    }

    @GetMapping("category/{id}")
    public ProductCategory getById(@PathVariable(name = "id") Long id) {
        return service.getById(id);
    }

    @PostMapping("add-category/{account-id}")
    public void addProductCategory(@RequestBody ProductCategory category,
                                   @PathVariable(name = "account-id") Long accountId) {
        service.addProductCategory(category, accountId);
    }

    @PutMapping("update-category/{category-id}")
    public void updateCategory(@RequestBody ProductCategory category,
                               @PathVariable(name = "category-id") Long categoryId) {
        service.updateCategory(category, categoryId);
    }

}
