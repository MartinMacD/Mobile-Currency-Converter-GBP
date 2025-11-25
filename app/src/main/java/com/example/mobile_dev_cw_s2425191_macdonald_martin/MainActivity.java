//
// Name                 Martin MacDonald
// Student ID           s2425191
// Programme of Study   BSc (Hons) Software Development
//

package com.example.mobile_dev_cw_s2425191_macdonald_martin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="https://www.fx-exchange.com/gbp/rss.xml";
    private ArrayList<Item> allItems;
    private ArrayList<Currency> allCurrency;
    private ListView currencyListView;
    private CustomCurrencyAdapter adapter;
    private FrameLayout fragmentContainer;
    private EditText searchBox;
    private String lastSelectedCurrencyCode = null;
    private String currentSearchText = "";
    private Executor executor = Executors.newSingleThreadExecutor();


    //When the app is created, set all variables up and setup search box listener.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        currencyListView = findViewById(R.id.allCurrencyList);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        searchBox = findViewById(R.id.searchBox);
        allItems = new ArrayList<>();
        allCurrency = new ArrayList<>();

        //Listener to update CustomCurrencyAdapter when the user enters something into the search bar.
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    currentSearchText = s.toString();
                    adapter.filter(currentSearchText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //When the app is shut, destroy the executor to prevent memory leaks.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor instanceof ExecutorService) {
            ((ExecutorService) executor).shutdownNow();
        }
    }

    public void onClick(View aview)
    {
        startProgress();
    }


    //Uses the executor to run background processes on the background thread and updates the UI on the main thread.
    public void startProgress()
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                fetchXMLData(urlSource);

                allItems.clear();
                parseXMLData();
                allCurrency.clear();
                convertItemsToCurrencies();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        });
    }

    //Used to update all UI elements.
    public void updateUI() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("RSS Data");
        builder.setMessage(result);
        builder.setPositiveButton("OK", null);
        builder.show();

        //Currency adapter, need to move to another thread.
        if (adapter == null) {
            // Runs the first time the adapter is created.
            adapter = new CustomCurrencyAdapter(MainActivity.this, allCurrency, new CustomCurrencyAdapter.OnConvertClickListener() {
                @Override
                public void onConvertClick(Currency currency) {
                    //Highlight the most recently selected currency.
                    lastSelectedCurrencyCode = currency.getCode();
                    showCurrencyConverterFragment(currency);
                }
            });
            currencyListView.setAdapter(adapter);
        } else {
            // Runs when the adapter is refreshed with new data.
            //Update the data in the adapter then search again.
            adapter.updateData(allCurrency);
            adapter.filter(currentSearchText);

            //Highlight the last selected currency.
            if (lastSelectedCurrencyCode != null) {
                adapter.setSelectedPosition(lastSelectedCurrencyCode);
            }
        }
    }

    //Used to fetch the XML feed.
    public void fetchXMLData(String url){
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";
        result = "";

        Log.d("MyTask","in run");

        try
        {
            Log.d("MyTask","in try");
            aurl = new URL(url);
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            while ((inputLine = in.readLine()) != null){
                result = result + inputLine;
            }
            in.close();
        }
        catch (IOException ae) {
            Log.e("MyTask", "ioexception");
        }

        result = cleanXMLData(result);
    }

    //Used to clean up the raw XML data feed.
    private String cleanXMLData(String xml){
        //If there is no xml data, return "";
        if(xml == null){
            return "";
        }

        //Find the start of the XML file.
        int start = xml.indexOf("<?");
        //Find the end of the XML file.
        int end = xml.indexOf("</rss>");
        //Only extract the substring if both of the tags exist and the end index occurs after the start index.
        //end + 6 includes the full rss tag in output.
        if (start >= 0 && end > start) {
            return xml.substring(start, end + 6);
        }
        return xml;
    }

    //Used to parse the XML data from the feed into Item objects.
    public void parseXMLData(){
        Item aItem = null;
        // Now that you have the xml data into result, you can parse it
        try {
            boolean insideAnItem = false;
            XmlPullParserFactory factory =
                    XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( result ) );

            //My pullparser
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG){
                    Log.e("Start tag", "Inside XML object");

                    if(xpp.getName().equalsIgnoreCase("item")){
                        insideAnItem = true;
                        aItem = new Item();
                        Log.e("Inside item", "New item found");
                    }else if (xpp.getName().equalsIgnoreCase("title")){
                        String temp = xpp.nextText();
                        if(insideAnItem){
                            aItem.setTitle(temp);
                            Log.d("MyTag", "Item title: " + temp);
                        }else{
                            Log.d("MyTag", "Some other title" + temp);
                        }
                    }else if(xpp.getName().equalsIgnoreCase("link")){
                        String temp = xpp.nextText();
                        if(insideAnItem){
                            aItem.setLink(temp);
                            Log.d("MyTag", "Item link: " + temp);
                        }else{
                            Log.d("MyTag", "Some other link" + temp);
                        }
                    }else if(xpp.getName().equalsIgnoreCase("guid")){
                        String temp = xpp.nextText();
                        if(insideAnItem){
                            aItem.setGuid(temp);
                            Log.d("MyTag", "Item guid: " + temp);
                        }else{
                            Log.d("MyTag", "Some other guid" + temp);
                        }
                    }else if(xpp.getName().equalsIgnoreCase("pubDate")){
                        String temp = xpp.nextText();
                        if(insideAnItem){
                            aItem.setPubDate(temp);
                            Log.d("MyTag", "Item pubDate: " + temp);
                        }else{
                            Log.d("MyTag", "Some other pubDate" + temp);
                        }
                    }else if(xpp.getName().equalsIgnoreCase("description")){
                        String temp = xpp.nextText();
                        if(insideAnItem){
                            aItem.setDescription(temp);
                            Log.d("MyTag", "Item description: " + temp);
                        }else{
                            Log.d("MyTag", "Some other description" + temp);
                        }
                    }else if(xpp.getName().equalsIgnoreCase("category")){
                        String temp = xpp.nextText();
                        if(insideAnItem){
                            aItem.setCategory(temp);
                            Log.d("MyTag", "Item category: " + temp);
                        }else{
                            Log.d("MyTag", "Some other category" + temp);
                        }
                    }
                } else if(eventType == XmlPullParser.END_TAG){
                    if(xpp.getName().equalsIgnoreCase("item")){
                        allItems.add(aItem);
                        insideAnItem = false;
                        Log.d("MyTag", "Item parsing complete");
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("Parsing","EXCEPTION" + e);
            //throw new RuntimeException(e);
        }catch (IOException e) {
            Log.e("Parsing","I/O EXCEPTION" + e);
            //throw new RuntimeException(e);
        }
    }

    //Used to convert the newly parsed item objects into currency objects and store them as an arraylist.
    private void convertItemsToCurrencies() {
        allCurrency.clear();
        for (Item item : allItems) {
            Currency currency = new Currency();
            currency.convertItemToCurrency(item);
            // Only add currency with an actual exchange rate
            if (currency.getGbpToCurrency() != 0.0) {
                allCurrency.add(currency);
            }
        }
    }

    //Used to display the currency converter fragment when the user clicks the convert button on a currency.
    public void showCurrencyConverterFragment(Currency currency) {
        //Set the fragment to be visible by the user.
        fragmentContainer.setVisibility(View.VISIBLE);

        //Create a new CurrencyConverterFragment object.
        CurrencyConverterFragment fragment = new CurrencyConverterFragment();

        //Add currencyCode, currencyName and currencyGbpToCurrency to the bundle and add that bundle to the fragment.
        Bundle args = new Bundle();
        args.putString("currencyCode", currency.getCode());
        args.putString("currencyName", currency.getName());
        args.putDouble("currencyGbpToCurrency", currency.getGbpToCurrency());
        fragment.setArguments(args);

        //Set the adapter to the one used by customCurrencyAdapter.
        fragment.setAdapter(adapter);

        //Create a new fragmentManager and fragmentTransaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Display the currencyConverterFragment in the container.
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }


}