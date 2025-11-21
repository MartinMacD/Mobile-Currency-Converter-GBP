package com.example.mobile_dev_cw_s2425191_macdonald_martin;

import android.util.Log;

public class Currency {
    private String name;
    private String code;
    private Double gbpToCurrency;
    private Double currencyToGbp;
    private String pubDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getGbpToCurrency() {
        return gbpToCurrency;
    }

    public void setGbpToCurrency(Double gbpToCurrency) {
        this.gbpToCurrency = gbpToCurrency;
    }

    public Double getCurrencyToGbp() {
        return currencyToGbp;
    }

    public void setCurrencyToGbp(Double currencyToGbp) {
        this.currencyToGbp = currencyToGbp;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }


    public void convertItemToCurrency(Item item){
        String[] titleParts = item.getTitle().split("/");
        String targetPart = titleParts[1];
        setName(targetPart.substring(0, targetPart.indexOf("(")));
        setCode(targetPart.substring(targetPart.indexOf("(") + 1, targetPart.indexOf(")")));
        Log.d("Currency object", "Name: " + this.name);
        Log.d("Currency object", "Code: " + this.code);

        String[] descParts = item.getDescription().split("=");
        String rateStr = descParts[1].trim().split(" ")[0];
        double rate = Double.parseDouble(rateStr);

        setGbpToCurrency(rate);
        setCurrencyToGbp(1.0 / rate);

        Log.d("Currency object", "GBPToCurrency: " + this.gbpToCurrency);
        Log.d("Currency object", "CurrencyToGBP: " + this.currencyToGbp);

        setPubDate(item.getPubDate());

        Log.d("Currency object", "PubDate: " + this.pubDate);

    }
}
