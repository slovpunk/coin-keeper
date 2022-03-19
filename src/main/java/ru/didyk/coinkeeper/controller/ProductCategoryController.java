package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.ProductCategory;
import ru.didyk.coinkeeper.service.ProductCategoryService;

import java.util.Optional;


@RestController
@RequestMapping(AccountController.PATH)
public class ProductCategoryController {

    private ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService service) {
        this.productCategoryService = service;
    }
    /*
    TODO: вылетает null
     */
    @GetMapping("category/{category-id}")
    public Optional<ProductCategory> getCategoryById(@PathVariable(name = "category-id") Long id) {
        return productCategoryService.getCategoryById(id);
    }

    @PostMapping("add-category/{account-id}")
    public void addProductCategory(@RequestBody ProductCategory category,
                                   @PathVariable(name = "account-id") Long accountId) {
        productCategoryService.addProductCategory(category, accountId);
    }

    @PutMapping("update-category/{category-id}")
    public void addPurchasesInCategory(@RequestBody ProductCategory category,
                               @PathVariable(name = "category-id") Long categoryId) {
        productCategoryService.addPurchasesInCategory(category, categoryId);
    }

    @DeleteMapping("delete-category/{category-id}")
    public void deleteProductCategory(@PathVariable(name = "category-id") Long categoryId) {
        productCategoryService.deleteCategory(categoryId);
    }
}
