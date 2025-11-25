//
// Name                 Martin MacDonald
// Student ID           s2425191
// Programme of Study   BSc (Hons) Software Development
//


package com.example.MacDonald_Martin_s2425191;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomCurrencyAdapter extends ArrayAdapter<Currency> {
    private Context context;
    private ArrayList<Currency> currencyList;
    private ArrayList<Currency> currencyListFull;
    //Used to hold the most recently selected currency so it can be highlighted to the user.
    private String selectedCurrencyCode = null;
    private FlagManager flagManager;
    private String currentSearchText = "";


    //Used to listen for when the user clicks on an item so it can be highlighted.
    public interface OnConvertClickListener {
        void onConvertClick(Currency currency);
    }

    private OnConvertClickListener listener;

    //Overload the default constructor with context and the list of currency to be displayed.
    public CustomCurrencyAdapter(Context applicationContext, ArrayList<Currency> currencyList, OnConvertClickListener listener){
        super(applicationContext, 0, currencyList);
        this.context = applicationContext;
        this.currencyList = currencyList;
        this.currencyListFull = new ArrayList<>(currencyList);
        this.listener = listener;
    }

    @NonNull
    @Override public View getView(int i, View view, @NonNull ViewGroup viewGroup){
        View listItem = view;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.currency_list_item, viewGroup, false);
        }

        //Get the current currency in list
        Currency currentCurrency = currencyList.get(i);

        //Create variables for each view in the UI.
        TextView name = listItem.findViewById(R.id.currencyName);
        TextView code = listItem.findViewById(R.id.currencyCode);
        TextView rate = listItem.findViewById(R.id.currencyRate);
        Button convertButton = listItem.findViewById(R.id.convertButton);
        ImageView currencyFlag = listItem.findViewById(R.id.currencyFlag);

        //Change convertButton background colour to grey.
        convertButton.setBackgroundColor(0xFFDDDDDD);


        //Setup the flagManager and get the flag that matches the currency code.
        flagManager = new FlagManager();
        int flag = flagManager.getFlag(currentCurrency.getCode());

        //Set the fields of the UI.
        name.setText(currentCurrency.getName());
        Log.d("CustomCurrencyAdapter", "Currency name: " + currentCurrency.getName());
        code.setText(currentCurrency.getCode());
        Log.d("CustomCurrencyAdapter", "Currency code: " + currentCurrency.getCode());
        rate.setText(String.format("1 GBP = %.2f %s", currentCurrency.getGbpToCurrency(), currentCurrency.getCode()));
        Log.d("CustomCurrencyAdapter", "Currency conversion: " + String.format("1 GBP = %.2f %s", currentCurrency.getGbpToCurrency(), currentCurrency.getCode()));
        currencyFlag.setImageResource(flag);
        Log.d("CustomCurrencyAdapter", "Currency flag: " + flag);



        //Used to set the background colour depending on how the currency compares to GBP.
        if (currentCurrency.getGbpToCurrency() < 1.0) {
            rate.setBackgroundColor(0xFFFF0000); // red
            rate.setTextColor(0xFF000000);
        } else if (currentCurrency.getGbpToCurrency() < 5.0) {
            rate.setBackgroundColor(0xFFFFA500); // orange
            rate.setTextColor(0xFF000000);
        } else if (currentCurrency.getGbpToCurrency() < 10.0) {
            rate.setBackgroundColor(0xFF32CD32); // light green
            rate.setTextColor(0xFF000000);
        } else if (currentCurrency.getGbpToCurrency() >= 10.0) {
            rate.setBackgroundColor(0xFF006400); // Dark green
            rate.setTextColor(0xFFFFFFFF);
        }

        //Highlight the selected currency with a grey background to allow the user to see which is selected.
        if (currentCurrency.getCode().equals(selectedCurrencyCode)) {
            listItem.setBackgroundColor(0xFFE0E0E0); // grey
        } else {
            listItem.setBackgroundColor(0x00000000); // transparent
        }


        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onConvertClick(currentCurrency);
                }
                //Highlight the currency selected by the user.
                setSelectedPosition(currentCurrency.getCode());
                Log.d("CustomCurrencyAdapter", "User clicked to convert: " + currentCurrency.getName() + " with code: " + currentCurrency.getCode());
            }
        });
        return listItem;
    }

    public void filter(String text){
        //Store the text typed into the search bar by the user.
        currentSearchText = text.toUpperCase().trim();
        //Clear the currencyList to make way for the search results.
        currencyList.clear();

        //If the user hasn't typed anything, display all the currencies.
        if (currentSearchText.isEmpty()) {
            currencyList.addAll(currencyListFull);
        } else {
            //Otherwise, find only the currencies with codes or names that match the entered text and add them to currencyList.
            for (Currency c : currencyListFull) {
                if (c.getCode().toUpperCase().contains(currentSearchText) || c.getName().toUpperCase().contains(currentSearchText)) {
                    currencyList.add(c);
                }
            }
        }
        //Update the data set.
        notifyDataSetChanged();
    }

    //Highlight the selected position of the currency the user has most recently chosen to convert.
    public void setSelectedPosition(String currencyCode){
        selectedCurrencyCode = currencyCode;
        // refresh the viewList
        notifyDataSetChanged();
    }

    //Clear the selected currency, de-highlighting the space in ListView.
    public void clearSelectedCurrency() {
        selectedCurrencyCode = null;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Currency> newList) {
        // Clear the full list first
        currencyListFull.clear();
        currencyListFull.addAll(newList);

        // Reapply the current filter
        filter(currentSearchText);
    }

}
