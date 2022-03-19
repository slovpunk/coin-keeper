package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.model.ProductCategory;

import java.util.Optional;

public interface ProductCategoryService {

    void addProductCategory(ProductCategory productCategory, Long accountId);

    void addPurchasesInCategory(ProductCategory productCategory, Long categoryId);

    void deleteCategory(Long id);

    Optional<ProductCategory> getCategoryById(Long id);
}
