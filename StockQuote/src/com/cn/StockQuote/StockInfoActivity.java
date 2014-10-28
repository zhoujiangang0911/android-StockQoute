package com.cn.StockQuote;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by 建刚 on 2014/10/27.
 */
public class StockInfoActivity extends Activity {

    private static final String TAG = "STOCKQUOTE";

    TextView companyNameTextView;
    TextView yearLowTextView;
    TextView yearHighTextView;
    TextView daysLowTextView;
    TextView daysHighTextView;
    TextView lastTextView;
    TextView changeTextView;
    TextView dailyTextView;

    static final String KEY_ITEM = "quote";
    static final String KEY_NAME = "Name";
    static final String KEY_YEAR_LOW = "YearLow";
    static final String KEY_YEAR_HIGH = "YearHigh";
    static final String KEY_DAYS_LOW = "DaysLow";
    static final String KEY_DAYS_HIGH = "DaysHigh";
    static final String KEY_LAST_TRADE_PRICE = "LastTradePrice";
    static final String KEY_CHANGE = "Change";
    static final String KEY_DAS_RANGE = "DaysRange";


    String name = "";
    String yearLow = "";
    String yearHigh = "";
    String daysLow = "";
    String daysHigh = "";
    String lastTradePrice = "";
    String change = "";
    String daysRange = "";


    String yahooURLFist = "asdfa";
    String yahooURLSecond = "adfdf";

    String[][] xmlPullParserArray = {{"AverageDailyVolume", "0"}, {"Change", "0"}, {"DaysLow", "0"},
            {"DaysHigh", "0"}, {"YearLow", "0"}, {"YearHigh", "0"}, {"MarketCapitalization", "0"},
            {"LastTradePriceOnly", "0"}, {"DaysRange", "0"}, {"Name", "0"}, {"Symbol", "0"}, {"Volume", "0"}, {"StockExchange", "0"}};

    int parserArrayIncrement = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);

        Intent intent = getIntent();
        String stockSymbol = intent.getStringExtra(MyActivity.STOCK_SYMBOL);

        companyNameTextView = (TextView) findViewById(R.id.companyNameTextView);
        yearLowTextView = (TextView) findViewById(R.id.yearLowTextView);
        yearHighTextView = (TextView) findViewById(R.id.yearLowTextView);
        daysLowTextView = (TextView) findViewById(R.id.daysLowTextView);
        daysHighTextView = (TextView) findViewById(R.id.daysHighTextView);
        lastTextView = (TextView) findViewById(R.id.lastTextView);
        changeTextView = (TextView) findViewById(R.id.changeTextView);
        dailyTextView = (TextView) findViewById(R.id.dailyTextView);

        Log.d(TAG, "Before url Creation" + stockSymbol);

        final String yalURL = yahooURLFist + stockSymbol + yahooURLSecond;

        new MyAssyncTask().execute();

    }


    private class MyAssyncTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {

//            try {
//                URL url = new URL(args[0]);
//                URLConnection connection;
//                connection = url.openConnection();
//
//                HttpURLConnection httpConnection = (HttpURLConnection) connection;
//
//                int responseCode = httpConnection.getResponseCode();
//
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    InputStream in = httpConnection.getInputStream();
//
//                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//
//                    DocumentBuilder db = dbf.newDocumentBuilder();
//
//                    Document dom = db.parse(in);
//
//                    Element docEle = dom.getDocumentElement();
//
//                    NodeList nl = docEle.getElementsByTagName("quote");
//
//                    if (nl != null && nl.getLength() > 0) {
//                        for (int i = 0; i < nl.getLength(); i++) {
//                            StockInfo theStock = getStockInformation(docEle);
//
//
//                            name = theStock.getName();
//
//                            yearLow = theStock.getYearLow();
//                            yearHigh = theStock.getYearHigh();
//                            daysLow = theStock.getDaysLow();
//                            daysHigh = theStock.getDaysHigh();
//                            lastTradePrice = theStock.getLastTradePriceonly();
//                            change = theStock.getChange();
//                            daysRange = theStock.getDayRange();
//
//
//                        }
//
//                    }
//                }
//
//            } catch (MalformedURLException e) {
//                Log.d(TAG, "MalformedURLException", e);
//            } catch (ParserConfigurationException e) {
//                Log.d(TAG, "ParserConfigurationException", e);
//            } catch (SAXException e) {
//                Log.d(TAG, "SAXException", e);
//            } catch (IOException e) {
//                Log.d(TAG, "IOException", e);
//            } finally {
//
//
//            }


            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new InputStreamReader(getURLData(args[0])));

                beginDocument(parser, "query");
                int  eventType = parser.getEventType();

                do{
                    nextElement(parser);

                    parser.next();

                    eventType = parser.getEventType();

                    if (eventType==XmlPullParser.TEXT){
                        String  valueFromXML = parser.getText();

                        xmlPullParserArray[parserArrayIncrement++][0]=valueFromXML;


                    }

                }while (eventType != XmlPullParser.END_DOCUMENT);



            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
               finally {

            }

            return null;
        }


        public InputStream getURLData(String url) throws Exception {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet method = new HttpGet(new URI(url));

            HttpResponse res = client.execute(method);

            return res.getEntity().getContent();


        }

        public final void beginDocument(XmlPullParser parser, String fistElementName) throws XmlPullParserException, IOException {
                int type=0;
//            while (){
//                    ;
//            }

            if (type != parser.START_TAG){
                throw new XmlPullParserException("no START tAG FOUND");

            }
            if (!parser.getName().equals(fistElementName)){
                throw new XmlPullParserException("UnException Start Tag Found"+parser.getName()+",expected"+fistElementName);

            }
        }

        public final void nextElement(XmlPullParser parser)throws  XmlPullParserException, IOException {
//            boolean type;
//               while ((type=parser.next()!=parser.START_TAG&&type!= XmlPullParser.END_DOCUMENT){
//                ;
//            }


        }




        protected void onPostExecute(String result) {
            companyNameTextView.setText(xmlPullParserArray[9][1]);
            yearLowTextView.setText("Year Low:" + xmlPullParserArray[4][1]);
            yearHighTextView.setText("Year High:" + xmlPullParserArray[5][1]);
            daysLowTextView.setText("Days Low:" + xmlPullParserArray[2][1]);
            daysHighTextView.setText("Days High:" + xmlPullParserArray[3][1]);
            lastTextView.setText("Last Trade Price:" + xmlPullParserArray[7][1]);
            changeTextView.setText("Change:" + xmlPullParserArray[1][1]);
            dailyTextView.setText("Days Range:" + xmlPullParserArray[8][1]);

        }


        private StockInfo getStockInformation(Element element) {
            String stockName = getTextValue(element, "Name");
            String YearLow = getTextValue(element, "YearLow");
            String YearHigh = getTextValue(element, "YearHigh");
            String DaysLow = getTextValue(element, "DaysLow");
            String DaysHigh = getTextValue(element, "DaysHigh");
            String LastTradePrice = getTextValue(element, "LastTradePrice");
            String Change = getTextValue(element, "Change");
            String DaysRange = getTextValue(element, "DaysRange");

            StockInfo theStock = new StockInfo(DaysLow, DaysHigh, YearLow, YearHigh, stockName, LastTradePrice, Change, DaysRange);

            return theStock;
        }

        private String getTextValue(Element entry, String tagName) {
            String tagValueToReturn = null;
            NodeList nl = entry.getElementsByTagName(tagName);
            if (nl != null && nl.getLength() > 0) {
                Element element = (Element) nl.item(0);
                tagValueToReturn = element.getFirstChild().getNodeValue();
            }


            return tagValueToReturn;
        }
    }


}
