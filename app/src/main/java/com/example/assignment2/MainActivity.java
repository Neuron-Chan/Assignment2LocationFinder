package com.example.assignment2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private DBHelper databaseHelper;
    private EditText addressInput;
    private TextView locationOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DBHelper(this);
        addressInput = findViewById(R.id.addressInput);
        locationOutput = findViewById(R.id.locationOutput);

        Button queryButton = findViewById(R.id.queryButton);
        Button addButton = findViewById(R.id.addButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button updateButton = findViewById(R.id.updateButton);

        queryButton.setOnClickListener(v -> queryLocation());
        addButton.setOnClickListener(v -> addLocation());
        deleteButton.setOnClickListener(v -> deleteLocation());
        updateButton.setOnClickListener(v -> updateLocation());

        // Populate the database if it is empty
        if (databaseHelper.getLocationByAddress("Downtown Toronto") == null) {
            databaseHelper.fetchLocations(); // Update method name here
        }
    }

    private void queryLocation() {
        String address = addressInput.getText().toString();
        double[] location = databaseHelper.getLocationByAddress(address);
        if (location != null) {
            locationOutput.setText("Latitude: " + location[0] + ", Longitude: " + location[1]);
        } else {
            locationOutput.setText("Location not found.");
        }
    }


    private void addLocation() {
        String address = addressInput.getText().toString();
        databaseHelper.addLocation(address, 43.0, -79.0); // Example coordinates
        locationOutput.setText("Location added.");
    }

    private void deleteLocation() {
        String address = addressInput.getText().toString();
        databaseHelper.deleteLocation(address);
        locationOutput.setText("Location deleted.");
    }

    private void updateLocation() {
        String oldAddress = addressInput.getText().toString();
        String newAddress = "New Address"; // Update to a new address
        databaseHelper.updateLocation(oldAddress, newAddress, 43.0, -79.0); // Example coordinates
        locationOutput.setText("Location updated.");
    }

    private void fetchLocations() {
        String[] areas = {
                "Downtown Toronto", "Scarborough", "Mississauga", "Brampton",
                "Markham", "Ajax", "Pickering", "Oshawa", "Richmond Hill",
                "Vaughan", "Toronto Pearson International Airport", "Etobicoke"
        };

        for (String area : areas) {
            String url = "https://nominatim.openstreetmap.org/search?city=" + area + "&format=json&limit=1";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.length() > 0) {
                                    JSONArray results = response.getJSONArray("results");
                                    if (results.length() > 0) {
                                        JSONObject location = results.getJSONObject(0);
                                        String address = location.getString("display_name");
                                        double latitude = location.getDouble("lat");
                                        double longitude = location.getDouble("lon");

                                        databaseHelper.addLocation(address, latitude, longitude);
                                        Log.d("LocationFetcher", "Inserted: " + address);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LocationFetcher", "Error fetching location: " + error.getMessage());
                        }
                    });

            Volley.newRequestQueue(this).add(jsonObjectRequest);
        }
    }
}
