package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.dto.ProductCategoryDTO;
import ru.didyk.coinkeeper.model.MoneyMovement;
import ru.didyk.coinkeeper.service.productCategory.MoneyMovementService;

import java.util.Optional;


@RestController
@RequestMapping(AccountController.PATH)
public class ProductCategoryController {

    private MoneyMovementService moneyMovementService;

    @Autowired
    public ProductCategoryController(MoneyMovementService service) {
        this.moneyMovementService = service;
    }

    @GetMapping("category/{category-id}")
    public Optional<MoneyMovement> getCategoryById(@PathVariable(name = "category-id") Long id) {
        return moneyMovementService.getCategoryById(id);
    }

//    @GetMapping("category")
//    public List<MoneyMovement> getAllProductCategory() {
//        return moneyMovementService.getAllProductCategories();
//    }

    @PostMapping("add-category/{account-id}")
    public void addProductCategory(@RequestBody ProductCategoryDTO category,
                                   @PathVariable(name = "account-id") Long accountId) {
        moneyMovementService.addProductCategory(category, accountId);
    }

    @PutMapping("update-category/{category-id}")
    public void addPurchasesInCategory(@RequestBody ProductCategoryDTO category,
                               @PathVariable(name = "category-id") Long categoryId) {
        moneyMovementService.addPurchasesInCategory(category, categoryId);
    }

    @DeleteMapping("delete-category/{category-id}")
    public void deleteProductCategory(@PathVariable(name = "category-id") Long categoryId) {
        moneyMovementService.deleteCategory(categoryId);
    }
}
