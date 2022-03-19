package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.model.ProductCategory;

public interface ProductCategoryService {

    void addProductCategory(ProductCategory productCategory, Long accountId);

    void addPurchasesInCategory(ProductCategory productCategory, Long categoryId);

    void deleteCategory(Long id);

    ProductCategory getCategoryById(Long id);
}
