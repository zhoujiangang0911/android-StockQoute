package com.cn.StockQuote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.Arrays;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    public final static String STOCK_SYMBOL = "com.cn.StockQuote.STOCK";

    private SharedPreferences stockSymbolsEntered;

    private TableLayout stockTableScrollView;

    private EditText stockSymbolEditText;

    Button enterSymbolButton;
    Button deleteStockButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        stockSymbolsEntered = getSharedPreferences("stockList", MODE_PRIVATE);

//        stockTableScrollView = (TableLayout) findViewById(R.id.stockScrollView);

        stockSymbolEditText = (EditText) findViewById(R.id.stockSymbolEditText);

        enterSymbolButton = (Button) findViewById(R.id.enterSymbolButton);
        deleteStockButton = (Button) findViewById(R.id.deleteStockButton);

        enterSymbolButton.setOnClickListener(enterSymbolButtonLister);
        deleteStockButton.setOnClickListener(deleteStockButtonLister);

        updateSaveStockList(null);


        }

    private void updateSaveStockList(String newStockSymbols){
        String [] stocks = stockSymbolsEntered.getAll().keySet().toArray(new String[0]);

        Arrays.sort(stocks,String.CASE_INSENSITIVE_ORDER);

        if(newStockSymbols != null){
                insertStockInScrollView(newStockSymbols,Arrays.binarySearch(stocks,newStockSymbols));
        }
        else{
            for (int i = 0; i <stocks.length ; i++) {
                insertStockInScrollView(stocks[i],i);
            }
        }

    }

    private void  insertStockInScrollView(String stock,int arrayIndex){

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newStockRow = inflater.inflate(R.layout.stock_quote_row,null);

        TextView  newStockTextView = (TextView)newStockRow.findViewById(R.id.stockSymbolTextView);

         newStockTextView.setText(stock);

        Button stockQuoteButton = (Button) newStockRow.findViewById(R.id.stockQuoteButton);

        stockQuoteButton.setOnClickListener(getStockActivityLister);


        Button quoteFromWebButton = (Button) newStockRow.findViewById(R.id.quoteFromWebButton);

        quoteFromWebButton.setOnClickListener(getStockFromWebsiteLister);

        stockTableScrollView.addView(newStockRow,arrayIndex);

    }

    private  void  saveStockSymbol(String newStock){
        String isTheStockNew = stockSymbolsEntered.getString(newStock,null);

        SharedPreferences.Editor preferencesEditor = stockSymbolsEntered.edit();
        preferencesEditor.putString(newStock,newStock);

        preferencesEditor.apply();

        if(isTheStockNew==null){
            updateSaveStockList(newStock);
        }

    }


    public View.OnClickListener enterSymbolButtonLister = new View.OnClickListener() {
        @Override
        public void onClick( View view) {
                  if(stockSymbolEditText.getText().length()>0){
                      saveStockSymbol(stockSymbolEditText.getText().toString());
                        stockSymbolEditText.setText("");
                      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                               imm.hideSoftInputFromWindow(stockSymbolEditText.getWindowToken(),0);
                  }else{
                      AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);

                      builder.setTitle(R.string.invalid_stock_symbol);

                      builder.setPositiveButton(R.string.ok,null);

                      builder.setMessage(R.string.missing_stock_symbol);

                      AlertDialog theAlerDialog = builder.create();

                      theAlerDialog.show();
                  }
        }
    };


    public View.OnClickListener deleteStockButtonLister = new View.OnClickListener() {
        @Override
        public void onClick( View view) {
            deleteAllStocks();

            SharedPreferences.Editor preferencesEditor = stockSymbolsEntered.edit();
            preferencesEditor.clear();
            preferencesEditor.apply();

        }
    };

    public View.OnClickListener getStockActivityLister = new View.OnClickListener() {
        @Override
        public void onClick( View view) {
            TableRow tablerow = (TableRow) view.getParent();

            TextView stockTextView = (TextView) tablerow.findViewById(R.id.enterSymbolTextView);

            String stockSymbol =stockTextView.getText().toString();

            Intent intent = new Intent(MyActivity.this,StockInfoActivity.class);

            intent.putExtra(STOCK_SYMBOL,stockSymbol);

            startActivity(intent);

        }
    };

    public View.OnClickListener getStockFromWebsiteLister = new View.OnClickListener() {
        @Override
        public void onClick( View view) {

            TableRow tablerow = (TableRow) view.getParent();

            TextView stockTextView = (TextView) tablerow.findViewById(R.id.enterSymbolTextView);

            String stockSymbol =stockTextView.getText().toString();

            String stockURL = getString(R.string.yahoo_stock_url)+stockSymbol;

            Intent getStockWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(stockURL));

            startActivity(getStockWebPage);

        }
    };


    private  void deleteAllStocks(){

        stockTableScrollView.removeAllViews();
    }
}
