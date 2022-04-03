package ru.didyk.coinkeeper.service.productCategory;

import ru.didyk.coinkeeper.dto.ProductCategoryDTO;
import ru.didyk.coinkeeper.model.UserCategory;
import ru.didyk.coinkeeper.model.MoneyMovement;

import java.util.List;
import java.util.Optional;

public interface MoneyMovementService {

    void addMoneyMovement(MoneyMovement moneyMovement);

    void addProductCategory(ProductCategoryDTO productCategory, Long accountId);

    void addPurchasesInCategory(ProductCategoryDTO productCategory, Long categoryId);

    void deleteCategory(Long id);

    Optional<MoneyMovement> getCategoryById(Long id);

    List<MoneyMovement> getAllMoneyMovement();
}
