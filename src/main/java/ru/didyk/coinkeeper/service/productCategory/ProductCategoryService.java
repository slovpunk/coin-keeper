package ru.didyk.coinkeeper.service.productCategory;

import ru.didyk.coinkeeper.dto.ProductCategoryDTO;
import ru.didyk.coinkeeper.model.ProductCategory;

import java.util.Optional;

public interface ProductCategoryService {

    void addProductCategory(ProductCategoryDTO productCategory, Long accountId);

    void addPurchasesInCategory(ProductCategoryDTO productCategory, Long categoryId);

    void deleteCategory(Long id);

    Optional<ProductCategory> getCategoryById(Long id);
}
