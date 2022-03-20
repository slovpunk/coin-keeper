package ru.didyk.coinkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.model.modelForParsing.CurrencyRate;
import ru.didyk.coinkeeper.parser.CurrencyRateParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd/MM/yyyy");;
    private String currentDay = formatForDateNow.format(new Date());
    private CurrencyRateParser currencyRateParser;
    private URL url;
    private LineNumberReader reader;
    private List<CurrencyRate> list;

    @Autowired
    public CurrencyRateServiceImpl(CurrencyRateParser currencyRateParser) {
        this.currencyRateParser = currencyRateParser;
    }

    /*
    При помощи этого метода пользователь может получить актуальный курс валют по ЦБ используя тикер
     */
    @Override
    public CurrencyRate getCurrencyRate(String currency) {
        try {
            url = new URL("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + currentDay);
            reader = new LineNumberReader(new InputStreamReader(url.openStream()));
            String string = reader.readLine();
            list = currencyRateParser.parse(string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.stream().filter(cur -> cur.getCharCode().equals(currency))
                .findFirst()
                .orElseThrow(() -> new ExpressionException("There is not currency"));
    }
}
