package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.model.modelForParsing.CurrencyRate;

public interface CurrencyService {

    CurrencyRate getCurrencyRate(String currency);
}
