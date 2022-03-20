package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.model.modelForParsing.CurrencyRate;

public interface CurrencyRateService {
    CurrencyRate getCurrencyRate(String currency);
}
