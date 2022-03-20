package ru.didyk.coinkeeper.parser;

import ru.didyk.coinkeeper.model.modelForParsing.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {

    List<CurrencyRate> parse(String ratesAsString);
}
