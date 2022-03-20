package ru.didyk.coinkeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.model.Currency;
import ru.didyk.coinkeeper.service.currencyRate.CurrencyRateService;
import ru.didyk.coinkeeper.service.currencyService.CurrencyService;

@RestController
@RequestMapping(AccountController.PATH)
public class CurrencyPortfolioController {

    private CurrencyService currencyService;
    private CurrencyRateService currencyRateService;

    @Autowired
    public CurrencyPortfolioController(CurrencyService currencyService, CurrencyRateService currencyRateService) {
        this.currencyService = currencyService;
        this.currencyRateService = currencyRateService;
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
    public void changeBalance(@RequestBody Currency currency,
                              @PathVariable(name = "currency-id") Long currencyId) {
         /*
        TODO: всё добавляется в рублях. Здесь необходима логика, которая переводит сумму в выбранную
         валюту по курсу ЦБ
         */
//        Currency currency1 = currencyService.getPortfolio(currencyId);
//        currency1.setBalance(currencyRateService.getCurrencyRate());
        currencyService.changeBalance(currencyId);
    }

    @DeleteMapping("portfolio/delete/{currency-id}")
    public void deleteCurrency(@PathVariable(name = "currency-id") Long currencyId) {
        currencyService.deleteCurrency(currencyId);
    }
}
