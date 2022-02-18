package com.autoload.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.LocationRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Location extends AppCompatActivity implements LocationListener {


    CardView location_card, location_result_card;
    TextView location_result_txt;
    LocationManager locationManager;
    String city, state, country;
    LottieAnimationView fetching_location;
    LottieAnimationView loading;

//    AutoCompleteTextView spn_country, spn_state, spn_city;
//    TextInputLayout til_country, til_state, til_city;
    Spinner country_spinner;
    Button done;

//    ArrayList<String> arrayList_states_names = new ArrayList<>();
//    ArrayAdapter<String> arrayAdapter_states_names;
//
//    ArrayList<String> arrayList_cities_names =  new ArrayList<>();
//    ArrayAdapter<String> arrayAdapter_cities_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        hook();

        turnOnGPS();

        findViewById(R.id.backImg).setOnClickListener(v -> finish());

        getAllCountries();

        location_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grantPermission();

                checkLocationIsEnabledOrNot();
                getLocation();
            }
        });

        findViewById(R.id.done_button).setOnClickListener(v ->{
            if(country_spinner.getSelectedItemId() != 0){
                SharedPreferences preferences = getSharedPreferences("Location", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

//                editor.putString("city", city);
//                editor.putString("state", state);
                editor.putString("country", country_spinner.getSelectedItem().toString());

                editor.remove("city").commit();
                editor.remove("state").commit();

                editor.apply();

                setLocationText();

                finish();
            }

        });
    }

    private void setLocationText() {

        SharedPreferences LocationSP = getSharedPreferences("Location", MODE_PRIVATE);

        if(LocationSP.contains("country")){
            if(LocationSP.contains("city"))
                first_frag.locationTextV.setText(LocationSP.getString("city", "") + ", " + LocationSP.getString("country", ""));
            else
                first_frag.locationTextV.setText(LocationSP.getString("country", ""));

        }else
            first_frag.locationTextV.setText(getString(R.string.default_country));
    }

    private void hook(){
        location_card = (CardView) findViewById(R.id.location_card);
        location_result_card = (CardView) findViewById(R.id.loaction_result_card);
        location_result_txt = (TextView) findViewById(R.id.location_result_txt);
        loading = (LottieAnimationView) findViewById(R.id.progressBar);
        fetching_location = (LottieAnimationView) findViewById(R.id.fetching_location);

        country_spinner = findViewById(R.id.country_spinner);

        done = findViewById(R.id.done_button);
    }

    void getAllCountries(){
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        countries.add("Select a Country");
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
//        arrayList_countries_names = countries;
//        for (String country : countries) {
//            arrayAdapter_countries_names.add(country);
//        }

        @SuppressLint("ResourceType") ArrayAdapter<String> CountryAdapter = new ArrayAdapter<>(this, R.layout.simple_textview, R.id.smp_textView, countries);
        country_spinner.setAdapter(CountryAdapter);


    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnabledOrNot() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(Location.this).setTitle("Enable GPS Service").setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent((Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(Location.this, "Please enable GPS service", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }else{
            loading.setVisibility(View.VISIBLE);
            fetching_location.setVisibility(View.VISIBLE);
        }
    }

    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        loading.setVisibility(View.VISIBLE);
        fetching_location.setVisibility(View.VISIBLE);
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            country = addresses.get(0).getCountryName();
            state = addresses.get(0).getAdminArea();
            city = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (city == null || state == null || country == null){
            Toast.makeText(Location.this, "Please try again", Toast.LENGTH_SHORT).show();
            finishThis ft = new finishThis();
            ft.start();
        }else{
            SharedPreferences preferences = getSharedPreferences("Location", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("city", city);
            editor.putString("state", state);
            editor.putString("country", country);

            editor.apply();

            setLocation();
            finishThis ft = new finishThis();
            ft.start();
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<android.location.Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void setLocation (){
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                location_result_txt.setText(city + ", " + country);
                location_result_card.setVisibility(View.VISIBLE);

                setLocationText();
            }
        });
    }

    class finishThis extends Thread {
        public void run(){
            try {
                Thread.sleep(1000);
                fetching_location.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }
    class onLoChLoadingTimer extends Thread{
        public void run(){
            try {
                fetching_location.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean turnOnGPS() {
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
        LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        settingsBuilder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(Location.this)
                .checkLocationSettings(settingsBuilder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response =
                            task.getResult(ApiException.class);
                } catch (ApiException ex) {
                    switch (ex.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                int LOCATION_SETTINGS_REQUEST = 100;
                                ResolvableApiException resolvableApiException =
                                        (ResolvableApiException) ex;
                                resolvableApiException
                                        .startResolutionForResult(Location.this,
                                                LOCATION_SETTINGS_REQUEST);
                            } catch (IntentSender.SendIntentException e) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                            break;

                    }
                }
            }
        });


        return true;
    }

}