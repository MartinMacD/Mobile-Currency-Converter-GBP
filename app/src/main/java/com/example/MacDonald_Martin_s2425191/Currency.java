//
// Name                 Martin MacDonald
// Student ID           s2425191
// Programme of Study   BSc (Hons) Software Development
//

package com.example.MacDonald_Martin_s2425191;

import android.util.Log;

public class Currency {
    private String name;
    private String code;
    private Double gbpToCurrency;
    private Double currencyToGbp;
    private String pubDate;

    //Getters and setters.
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


    //Used for taking an Item object and convert it into a currency object.
    public void convertItemToCurrency(Item item){
        //Take the title from the Item and split it where the / is and store both parts as an array.
        String[] titleParts = item.getTitle().split("/");
        //As we only need the currency that isn't GBP, set the targetPart to that side of the array.
        String targetPart = titleParts[1];
        //Set the name of the currency using a substring which pulls all text before the (.
        setName(targetPart.substring(0, targetPart.indexOf("(")));
        //Set the code of the currency using a substring which pulls the text between the ( and ).
        setCode(targetPart.substring(targetPart.indexOf("(") + 1, targetPart.indexOf(")")));
        Log.d("Currency object", "Name: " + this.name);
        Log.d("Currency object", "Code: " + this.code);

        //Take the description from the Item and split it where the = is and store both parts as an array.
        String[] descParts = item.getDescription().split("=");
        //We only need the other currencies conversion to GBP so use that side of the split.
        //Trim it to remove trailing whitespace and split it again but store the number as rateStr.
        String rateStr = descParts[1].trim().split(" ")[0];
        //Convert the String of our currency conversion into Double.
        double rate = Double.parseDouble(rateStr);

        //Set the GbpToCurrency as the rate.
        setGbpToCurrency(rate);
        //Set the currencyToGbp as 1 / rate because we know that GBP will always be represented as 1,
        //Because it is the subject in all queries.
        setCurrencyToGbp(1.0 / rate);

        Log.d("Currency object", "GBPToCurrency: " + this.gbpToCurrency);
        Log.d("Currency object", "CurrencyToGBP: " + this.currencyToGbp);

        //Set the pubDate to the pubDate within the Item.
        setPubDate(item.getPubDate());

        Log.d("Currency object", "PubDate: " + this.pubDate);

    }
}
