package com.daothiphuongthuy.k224112e;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daothiphuongthuy.adapter.GoldPriceAdapter;
import com.daothiphuongthuy.models.GoldPrice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GoldPriceActivity extends AppCompatActivity {
    ListView lvGold;
    GoldPriceAdapter adapter;
    ArrayList<GoldPrice> goldPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category); // Reusing activity_category which has a ListView
        
        addViews();
        loadGoldPrices();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        lvGold = findViewById(R.id.lvCategory);
        goldPrices = new ArrayList<>();
        adapter = new GoldPriceAdapter(this, R.layout.item_gold_price);
        lvGold.setAdapter(adapter);
    }

    private void loadGoldPrices() {
        // Multi-threading: Fetch data in a background thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://gw.vnexpress.net/cr/?name=tygia_vangv202206");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse JSON
                    JSONObject jsonRoot = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonRoot.getJSONObject("data")
                                                 .getJSONObject("data")
                                                 .getJSONObject("chart")
                                                 .getJSONArray("ha_noi_pnj");

                    final ArrayList<GoldPrice> tempList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String label = obj.getString("label");
                        String date = obj.getString("date_label");
                        long buy = obj.getLong("buy");
                        long sell = obj.getLong("sell");
                        tempList.add(new GoldPrice(label, date, buy, sell));
                    }

                    // Update UI on Main Thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clear();
                            adapter.addAll(tempList);
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (Exception e) {
                    Log.e("GOLD_PRICE_ERROR", e.toString());
                }
            }
        });
        thread.start();
    }
}
