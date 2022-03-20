package ru.didyk.coinkeeper.service;

import ru.didyk.coinkeeper.model.Currency;

public interface CurrencyService {

    void addCurrency(Currency currency);

    void deleteCurrency(Long currencyId);

    void changeBalance(Long id);

    Currency getPortfolio(Long id);
}
