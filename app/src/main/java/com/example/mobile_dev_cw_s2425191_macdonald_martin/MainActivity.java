//
// Name                 Martin MacDonald
// Student ID           s2425191
// Programme of Study   BSc (Hons) Software Development
//

package com.example.mobile_dev_cw_s2425191_macdonald_martin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="https://www.fx-exchange.com/gbp/rss.xml";
    private ArrayList<Item> allItems;
    private ArrayList<Currency> allCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        // More Code goes here

    }

    public void onClick(View aview)
    {
        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;
        public Task(String aurl){
            url = aurl;
        }
        @Override
        public void run(){
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.d("MyTask","in run");

            allItems = new ArrayList<>();

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

            //Clean up any leading garbage characters
            int i = result.indexOf("<?"); //initial tag
            result = result.substring(i);

            //Clean up any trailing garbage at the end of the file
            i = result.indexOf("</rss>"); //final tag
            result = result.substring(0, i + 6);

            parseXMLData();

            allCurrency = new ArrayList<>();

            for(Item item : allItems){
                Currency currency = new Currency();
                currency.convertItemToCurrency(item);
                allCurrency.add(currency);
            }

            ArrayList<Currency> mainCurrencies = new ArrayList<>();
            for (Currency c : allCurrency) {
                if (c.getCode().equals("USD") || c.getCode().equals("EUR") || c.getCode().equals("JPY")) {
                    mainCurrencies.add(c);
                }
            }

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(result);
                }
            });
        }
    }

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


}