package ru.didyk.coinkeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.Currency;
import ru.didyk.coinkeeper.service.CurrencyService;

@RestController
@RequestMapping(AccountController.PATH)
public class CurrencyPortfolioController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyPortfolioController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("portfolio/{portfolio-id}")
    public Currency getPortfolioById(@PathVariable(name = "portfolio-id") Long portfolioId) {
        return currencyService.getPortfolio(portfolioId);
    }

    @PostMapping("portfolio")
    public void addCurrencyInPortfolio(@RequestBody Currency currency) {
        /*
        TODO: всё добавляется в рублях. Здесь необходима логика, которая переводит сумму в выбранную
         валюту по курсу ЦБ
         */
        currencyService.addCurrency(currency);
    }

    @PutMapping("portfolio/update/{currency-id}")
    public void changeBalance(@PathVariable(name = "currency-id") Long currencyId) {
        currencyService.changeBalance(currencyId);
    }

    @DeleteMapping("portfolio/delete/{currency-id}")
    public void deleteCurrency(@PathVariable(name = "currency-id") Long currencyId) {
        currencyService.deleteCurrency(currencyId);
    }
}
