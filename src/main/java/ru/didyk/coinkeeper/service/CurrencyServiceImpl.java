package ru.didyk.coinkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.model.Currency;
import ru.didyk.coinkeeper.repository.CurrencyRepository;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void addCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    @Override
    public void deleteCurrency(Long currencyId) {
        currencyRepository.delete(currencyRepository.getById(currencyId));
    }

    @Override
    public void changeBalance(Long id) {
        currencyRepository.save(currencyRepository.getById(id));
    }

    @Override
    public Currency getPortfolio(Long id) {
        return currencyRepository.findById(id).get();
    }
}
