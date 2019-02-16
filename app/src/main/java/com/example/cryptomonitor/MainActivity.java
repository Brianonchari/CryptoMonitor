package com.example.cryptomonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView bitcoinTv, etheriumTv,textViewCurrency;
//    private static String URL_DATA = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR";
    private static String URL_DATA = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=NGN,USD,EUR,JPY,GBP,AUD,CAD,CHF,CNY,KES,GHS,UGX,ZAR,XAF,NZD,MYR,BND,GEL,RUB,I";
    private RecyclerView recyclerView;
    private List<CardItems> cardItemsList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init Toolbar
//         Toolbar toolbar = findViewById(R.id.toolBar);
//         setSupportActionBar(null);
//         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init TextView objects

        bitcoinTv = findViewById(R.id.bitcoinTv);
        etheriumTv = findViewById(R.id.etheriumTv);
        textViewCurrency = findViewById(R.id.textViewCurrency);

        //init recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardItemsList = new ArrayList<>();


        loadURLData();

        adapter = new MyAdapter(cardItemsList, this);
        recyclerView.setAdapter(adapter);

    }

    public void loadURLData(){
        /**
         * This class makes and api call
         */
        JsonObjectRequest DataRequest = new JsonObjectRequest(Request.Method.GET, URL_DATA, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject btc_values = response.getJSONObject("BTC".trim());
                            JSONObject eth_values = response.getJSONObject("ETH".trim());

                            Iterator<?> keysBTC = btc_values.keys();
                            Iterator<?> keysETH = eth_values.keys();


                            while (keysBTC.hasNext() && keysETH.hasNext()) {
                                String keyBTC = (String) keysBTC.next();
                                String keyETH = (String) keysETH.next();

                                CardItems card = new CardItems(keyBTC, btc_values.getDouble(keyBTC), eth_values.getDouble(keyETH));
                                cardItemsList.add(card);
                            }

                            adapter = new MyAdapter(cardItemsList, getApplicationContext());
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(DataRequest);

    }
}
