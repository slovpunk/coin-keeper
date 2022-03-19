package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.model.ProductCategory;

public interface ProductCategoryService {

    void addProductCategory(ProductCategory productCategory);

    ProductCategory getById(Long id);
}
