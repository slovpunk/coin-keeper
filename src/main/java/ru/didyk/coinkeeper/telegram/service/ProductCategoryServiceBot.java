package ru.didyk.coinkeeper.telegram.service;

import ru.didyk.coinkeeper.telegram.entity.ProductCategoryBot;

public interface ProductCategoryServiceBot {

    void setProductCategory(long chatId, ProductCategoryBot productCategory);
    ProductCategoryBot getProductCategory(long chatId);
}
