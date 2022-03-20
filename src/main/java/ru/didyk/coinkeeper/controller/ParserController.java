package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.didyk.coinkeeper.model.modelForParsing.CurrencyRate;
import ru.didyk.coinkeeper.service.CurrencyService;

@RestController
@RequestMapping(AccountController.PATH)
public class ParserController {

    private CurrencyService currencyService;

    @Autowired
    public ParserController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("course/{char-code}")
    public CurrencyRate getCourse(@PathVariable(name = "char-code") String charCode) {
        return currencyService.getCurrencyRate(charCode);
    }
}
