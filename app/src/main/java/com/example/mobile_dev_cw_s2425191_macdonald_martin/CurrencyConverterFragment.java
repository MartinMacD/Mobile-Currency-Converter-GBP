package com.example.mobile_dev_cw_s2425191_macdonald_martin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CurrencyConverterFragment extends Fragment {

    private EditText inputAmount;
    private TextView convertedFrom ;
    private TextView convertedAmount ;
    private TextView convertedTo;
    private ImageButton swapButton;
    private Button convertButton;
    private String currencyCode;
    private double currencyGbpToCurrency;
    private ImageButton closeButton;
    private CustomCurrencyAdapter adapter;
    private ImageView convertedFromFlag;
    private ImageView convertedToFlag;
    private FlagManager flagManager;

    //This adapter is used to de-select an item in listview when the X button is selected.
    public void setAdapter(CustomCurrencyAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_currency_converter, container, false);

        //Set XML views to local variables.
        inputAmount = v.findViewById(R.id.inputAmount);
        convertedFrom = v.findViewById(R.id.convertedFrom);
        convertedAmount = v.findViewById(R.id.convertedAmount);
        convertedTo = v.findViewById(R.id.convertedTo);
        swapButton = v.findViewById(R.id.swapButton);
        convertButton = v.findViewById(R.id.convertButton);
        closeButton = v.findViewById(R.id.closeButton);
        convertedFromFlag = v.findViewById(R.id.convertedFromFlag);
        convertedToFlag = v.findViewById(R.id.convertedToFlag);

        //Change convertButton background colour to grey.
        convertButton.setBackgroundColor(0xFFDDDDDD);

        //Get the bundle passed in from MainActivity and set the local variables to the values contained within.
        Bundle args = getArguments();
        if (args != null) {
            currencyCode = args.getString("currencyCode");
            convertedTo.setText(currencyCode);
            currencyGbpToCurrency = args.getDouble("currencyGbpToCurrency");
        }

        //Setup the flagManager and get the flag that matches the convertToCode.
        //No need to do the other flag as it will always be GB.
        flagManager = new FlagManager();
        int convertedToflag = flagManager.getFlag(currencyCode);
        convertedToFlag.setImageResource(convertedToflag);

        //Listen for when the swapButton is clicked and then run swapCurrencies().
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapCurrencies();
            }
        });

        //Listen for when the convertButton is clicked and then run convertCurrency().
        convertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                convertCurrency();
            }
        });

        //Listen for when the closeButton is clicked and then run closeFragment().
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                closeFragment();
            }
        });


        return v;
    }

    private void convertCurrency(){
        //Get the amount inputted by the user.
        String input = inputAmount.getText().toString().trim();
        //If the user has entered nothing, set the convertedAmount to 0.
        if (input.isEmpty()) {
            convertedAmount.setText("0");
            return;
        }

        //Try to calculate the currency conversion and display it on the screen.
        try {
            double amount = Double.parseDouble(input);
            double result = amount * currencyGbpToCurrency;

            convertedAmount.setText(String.format("%.2f", result));
            //If there's an error, set the convertedAmount to 0 to prevent a crash.
        } catch (NumberFormatException e) {
            convertedAmount.setText("0");
        }

    }

    private void swapCurrencies(){
        //Set the current text of the currency labels to variables and then swap them on screen.
        String fromText = convertedFrom.getText().toString();
        String toText = convertedTo.getText().toString();
        convertedFrom.setText(toText);
        convertedTo.setText(fromText);

        //Swap the current flag with the opposite flag and display them to the user.
        int fromFlag = flagManager.getFlag(fromText);
        int toFlag = flagManager.getFlag(toText);
        convertedFromFlag.setImageResource(toFlag);
        convertedToFlag.setImageResource(fromFlag);

        //Even though i've check before that the currency value isn't 0,
        //It's best to check here as a divide by zero will crash the program.
        if (currencyGbpToCurrency != 0) {
            //Invert the rate to now be converting in the right direction.
            currencyGbpToCurrency = 1 / currencyGbpToCurrency;
        }else {
            currencyGbpToCurrency = 1.0;
        }

        //Immediately convert after the swap to see the new values.
        convertCurrency();
    }

    private void closeFragment(){
        //If there is an adapter, clear the currently selected currency, de-highlighting it in list view.
        if (adapter != null) {
            adapter.clearSelectedCurrency();
        }

        //Setup fragmentmanager and fragment to the current fragment.
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        //If there is a fragment, remove it.
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
        //Hide the container for the fragment, making the listview fully visible again.
        View container = requireActivity().findViewById(R.id.fragmentContainer);
        container.setVisibility(View.GONE);
    }




}
