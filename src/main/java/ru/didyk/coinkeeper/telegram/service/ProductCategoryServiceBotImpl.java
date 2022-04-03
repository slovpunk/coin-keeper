package ru.didyk.coinkeeper.telegram.service;

import org.springframework.stereotype.Component;
import ru.didyk.coinkeeper.telegram.entity.ProductCategoryBot;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductCategoryServiceBotImpl implements ProductCategoryServiceBot {

    private final Map<Long, ProductCategoryBot> originalCurrency = new HashMap<>();

    @Override
    public void setProductCategory(long chatId, ProductCategoryBot productCategory) {
        originalCurrency.put(chatId, productCategory);
    }

    @Override
    public ProductCategoryBot getProductCategory(long chatId) {
        return originalCurrency.getOrDefault(chatId, ProductCategoryBot.FOOD);
    }
}
