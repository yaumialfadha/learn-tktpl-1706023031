package id.ac.ui.cs.mobileprogramming.yaumialfadha;;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button buttonScan;
    private int size = 0;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> beforePostList = new ArrayList<>();
    private ArrayAdapter adapter;
    private WifiManager wifiManager;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);

        buttonScan = findViewById(R.id.scanBtn);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanWifi();
            }
        });

        listView = findViewById(R.id.wifiList);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Wifi is disabled .. We need to enable it", Toast.LENGTH_LONG).show();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        scanWifi();
    }

    private void scanWifi() {
        arrayList.clear();
        beforePostList.clear();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        boolean success = wifiManager.startScan();
        if (!success) {
            scanFailure();
        } else {
            Log.d("LANGKITANG", "MWEHEHE");
        }
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_LONG).show();
    }

    private void scanFailure() {
        Log.d("LANGKITANG", String.valueOf(checkPermission()));
        List<ScanResult> results = wifiManager.getScanResults();
        Log.d("LANGKITANG", results.toString());
    }

    public boolean checkPermission() {
        boolean permission = true;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> requiringList = new ArrayList<>();

            permission = this.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED;
            Log.d("LANGKITANG", "ACCESS_WIFI_STATE: " + permission);
            if (!permission) {
                requiringList.add(Manifest.permission.ACCESS_WIFI_STATE);
            }

            permission = this.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED;
            Log.d("LANGKITANG", "CHANGE_WIFI_STATE: " + permission);
            if (!permission) {
                requiringList.add(Manifest.permission.CHANGE_WIFI_STATE);
            }

            permission = this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            Log.d("LANGKITANG", "ACCESS_COARSE_LOCATION: " + permission);
            if (!permission) {
                requiringList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            permission = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            Log.d("LANGKITANG", "ACCESS_FINE_LOCATION: " + permission);
            if (!permission) {
                requiringList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (requiringList.size() > 0) {
                String[] stringArray = requiringList.toArray(new String[0]);
                this.requestPermissions(stringArray, 1042);
            }
        }
        return permission;
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("LANGKITANG", "KEPANGGIL GAK");

            results = wifiManager.getScanResults();
            unregisterReceiver(this);

            Log.d("LANGKITANG", results.toString());

            for (ScanResult scanResult : results) {
                beforePostList.add(scanResult.SSID + " - " + scanResult.capabilities);
                Log.d("LANGKITANG", scanResult.SSID + " - " + scanResult.capabilities);
//                adapter.notifyDataSetChanged();
            }
            postRequest(beforePostList);
        }
    };

    private void postRequest(final ArrayList<String> connections) {
        String url = "https://postman-echo.com/post";

        JSONObject body = new JSONObject();
        try {
            body.put("connections", connections);
        } catch (JSONException e) {
            Log.d("LANGKITANG", e.toString());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("LANGKITANG", response.toString());

                            JSONObject data = response.getJSONObject("data");
                            String connectionsData = data.getString("connections");

                            String modifiedConnData = connectionsData.substring(1, connectionsData.length() - 1);
                            String[] splittedConnData = modifiedConnData.split(",");

                            for (int i = 0; i < splittedConnData.length; i++) {
                                Log.d("LANGKITANG", String.valueOf(i));
                                arrayList.add(splittedConnData[i]);
                                Log.d("LANGKITANG", splittedConnData[i]);
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            Log.d("LANGKITANG", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LANGKITANG", error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        mQueue.add(request);
    }
}