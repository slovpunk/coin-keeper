//package ru.didyk.coinkeeper.parser;
//
//import ru.didyk.coinkeeper.model.modelForParsing.CurrencyRate;
//import ru.didyk.coinkeeper.parser.parser.CurrencyRateParserXml;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.LineNumberReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///*
//Получение курсов валют с сайта ЦБ
// */
//public class Main2 {
//    public static void main(String[] args) throws IOException {
//
//        CurrencyRateParserXml currencyRateParserXml = new CurrencyRateParserXml();
//
//        Date date = new Date();
//        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd/MM/yyyy");
//        String currentDay = formatForDateNow.format(date);
//
//        try {
//            URL url = new URL("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + currentDay);
//            try {
//                LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
//                String string = reader.readLine();
//                List<CurrencyRate> list = currencyRateParserXml.parse(string);
//                Map<String, String> map = new HashMap<>();
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getCharCode().equals("EUR") || list.get(i).getCharCode().equals("USD")) {
//                        map.put(list.get(i).getCharCode(), list.get(i).getValue());
//                    }
//                }
//                for (Map.Entry<String, String> item : map.entrySet()) {
//                    System.out.printf("%s - %s \n", item.getKey(), item.getValue());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//        }
//    }
//}
