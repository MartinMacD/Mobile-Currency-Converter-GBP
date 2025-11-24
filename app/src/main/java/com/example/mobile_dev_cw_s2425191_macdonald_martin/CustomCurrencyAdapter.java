//
// Name                 Martin MacDonald
// Student ID           s2425191
// Programme of Study   BSc (Hons) Software Development
//


package com.example.mobile_dev_cw_s2425191_macdonald_martin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomCurrencyAdapter extends ArrayAdapter<Currency> {
    Context context;
    ArrayList<Currency> currencyList;
    //Used to hold position of most recently selected currency so it can be highlighted to the user.
    private int selectedPosition = -1;

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


        //Set the fields of the UI.
        name.setText(currentCurrency.getName());
        code.setText(currentCurrency.getCode());
        rate.setText(String.format("1 GBP = %.2f %s", currentCurrency.getGbpToCurrency(), currentCurrency.getCode()));

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
        if (i == selectedPosition) {
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
                setSelectedPosition(i);
            }
        });
        return listItem;
    }

    //Highlight the selected position of the currency the user has most recently chosen to convert.
    public void setSelectedPosition(int position){
        selectedPosition = position;
        // refresh the viewList
        notifyDataSetChanged();
    }

}
