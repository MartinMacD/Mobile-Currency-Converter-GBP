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
    private boolean isSwapped = false;

    //This adapter is used to de-select an item in listview when the X button is selected.
    public void setAdapter(CustomCurrencyAdapter adapter) {
        this.adapter = adapter;
    }

    //Save the current state of the fragment so it can be restored later
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isSwapped", isSwapped);
        outState.putString("inputAmount", inputAmount.getText().toString());
        outState.putString("convertedFrom", convertedFrom.getText().toString());
        outState.putString("convertedTo", convertedTo.getText().toString());
        outState.putDouble("conversionRate", currencyGbpToCurrency);
        outState.putString("convertedAmount", convertedAmount.getText().toString());
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

        flagManager = new FlagManager();

        //Change convertButton background colour to grey.
        convertButton.setBackgroundColor(0xFFDDDDDD);

        //If there are arguments in the bundle, set them to local variables.
        if (getArguments() != null) {
            currencyCode = getArguments().getString("currencyCode");
            currencyGbpToCurrency = getArguments().getDouble("currencyGbpToCurrency");
        }

        //If there is an instance state saved, restore the fragments state and UI
        if (savedInstanceState != null) {

            isSwapped = savedInstanceState.getBoolean("isSwapped");

            inputAmount.setText(savedInstanceState.getString("inputAmount", ""));
            convertedAmount.setText(savedInstanceState.getString("convertedAmount", ""));

            convertedFrom.setText(savedInstanceState.getString("convertedFrom"));
            convertedTo.setText(savedInstanceState.getString("convertedTo"));

            currencyGbpToCurrency = savedInstanceState.getDouble("conversionRate");

            // restore flags
            convertedFromFlag.setImageResource(
                    flagManager.getFlag(convertedFrom.getText().toString()));
            convertedToFlag.setImageResource(
                    flagManager.getFlag(convertedTo.getText().toString()));

        } else {
            // Otherwise if there is no instance saved, set the local variables to defaults/what was loaded from the bundle.
            convertedFrom.setText("GBP");
            convertedTo.setText(currencyCode);
            convertedFromFlag.setImageResource(flagManager.getFlag("GBP"));
            convertedToFlag.setImageResource(flagManager.getFlag(currencyCode));
        }


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

    //Used to convert the currency selected.
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

    //Swap the currencies and their flags.
    private void swapCurrencies(){
        //Set isSwapped to be the opposite of what it is now.
        isSwapped = !isSwapped;

        //Set the current text of the currency labels to variables and then swap them on screen.
        String fromText = convertedFrom.getText().toString();
        String toText = convertedTo.getText().toString();
        convertedFrom.setText(toText);
        convertedTo.setText(fromText);

        //Swap the UI flags using the flagManager.
        convertedFromFlag.setImageResource(flagManager.getFlag(toText));
        convertedToFlag.setImageResource(flagManager.getFlag(fromText));

        //Even though i've check before that the currency value isn't 0,
        //It's best to check here as a divide by zero will crash the program.
        if (currencyGbpToCurrency != 0) {
            //Invert the rate to now be converting in the right direction.
            currencyGbpToCurrency = 1 / currencyGbpToCurrency;
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

    //Used to update the current currency.
    public void updateCurrency(String code, String name, double rate) {
        currencyCode = code;
        currencyGbpToCurrency = rate;


        //If the swap button hasn't been clicked, set it up for GBP > selected currency, otherwise do the opposite.
        if (!isSwapped) {
            // GBP > selected currency
            convertedFrom.setText("GBP");
            convertedTo.setText(code);
            convertedFromFlag.setImageResource(flagManager.getFlag("GBP"));
            convertedToFlag.setImageResource(flagManager.getFlag(code));
        } else {
            // Selected currency > GBP
            convertedFrom.setText(code);
            convertedTo.setText("GBP");
            convertedFromFlag.setImageResource(flagManager.getFlag(code));
            convertedToFlag.setImageResource(flagManager.getFlag("GBP"));
            currencyGbpToCurrency = 1 / rate;
        }

        //Now convert the currency using the newly setup variables.
        convertCurrency();
    }




}
