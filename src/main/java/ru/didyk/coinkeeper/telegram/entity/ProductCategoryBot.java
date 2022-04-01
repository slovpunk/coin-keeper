package ru.didyk.coinkeeper.telegram.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategoryBot {

    FOOD(1), CLOTHES(2), DOMESTIC(3), AUTO(4), ENTERTAINMENT(5);

    private final int id;
    }
