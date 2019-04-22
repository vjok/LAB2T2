package com.example.predator.lab2t2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView = null;
    public String data;
    ArrayList<String> test = new ArrayList<>();
    String[] stockIDs = {"NOK", "FB", "GOOGL", "AAPL"};
    String[] names = {"Nokia", "Facebook", "Google", "Apple"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < stockIDs.length; i++) {
            getData(stockIDs[i], names[i]);
        }

    }

    public void getData(final String id, final String name) {

        String addressToPass = "https://financialmodelingprep.com/api/company/price/" + id;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, addressToPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                data = response.replace("<pre>", "");
                String stringToAdd = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String price = jsonObject.getJSONObject(id).getString("price");
                    stringToAdd = name + ": " + price + " USD";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                test.add(stringToAdd);
                setupView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void setupView() {
        listView = findViewById(R.id.listView);
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, test);
        listView.setAdapter(adapter);
    }
}
