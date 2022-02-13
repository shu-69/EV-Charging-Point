package com.unknown.proj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvStationsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvStationsListFragment extends Fragment {

    View view;

    LinearLayout topLinearContainer;
    LottieAnimationView loadingAnim;
    CardView errorMessageCard, retryCard, controls;
    ChipGroup connectorTypes;
    TextView errorText;
    Chip c1, c2, c3, c4, c5, c6, c7;

    ArrayList<String> selectedChipData = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EvStationsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvStationsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvStationsListFragment newInstance(String param1, String param2) {
        EvStationsListFragment fragment = new EvStationsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ev_stations_list, container, false);
        topLinearContainer = view.findViewById(R.id.topLinear);
        loadingAnim = view.findViewById(R.id.loading_anim);
        errorMessageCard = view.findViewById(R.id.error_message_card);
        errorText = view.findViewById(R.id.error_message_text);
        retryCard = view.findViewById(R.id.retry_card);
        controls = view.findViewById(R.id.controls);
        connectorTypes = view.findViewById(R.id.connector_types_selector);
        c1 = view.findViewById(R.id.c1);
        c2 = view.findViewById(R.id.c2);
        c3 = view.findViewById(R.id.c3);
        c4 = view.findViewById(R.id.c4);
        c5 = view.findViewById(R.id.c5);
        c6 = view.findViewById(R.id.c6);
        c7 = view.findViewById(R.id.c7);

        //addEVDetailsCard();

        /**
         * Response Fields
         *
         * total_results(integer)
         * station_locator_url (String)
         * fuel_type_code(string) -> CNG, ELEC, LPG, BD, E85, HY, LNG
         * station_name(string)
         * street_address(string)
         * intersection_directions(string)
         * city(string)
         * state(string)
         * zip(string)
         * station_phone(string)
         * status_code(string) -> E (Available), P (Planned), T (Temporarily Unavailable)
         * access_code(string) -> public, private
         * ev_level1_evse_num(integer) -> (Number of Level 1 EVSE Ports)
         * ev_level2_evse_num(integer) -> (Number of Level 2 EVSE Ports)
         * ev_dc_fast_num(integer) -> (Number of DC Fast EVSE Ports)
         * ev_pricing(String)
         * latitude(decimal)
         * longitude(decimal)
         * ev_connector_types(string) -> NEMA1450 (NEMA 14-50), NEMA515 (NEMA 5-15), NEMA520 (NEMA 5-20), J1772 (J1772), J1772COMBO (CCS), CHADEMO (CHAdeMO), TESLA (Tesla)
         *
         */

        if (checkInternetConnection()) {
            getEvStations("OR", "NEMA1450,NEMA515,NEMA520,J1772,J1772COMBO,CHADEMO,TESLA");
            //Toast.makeText(getContext(), "Good", Toast.LENGTH_LONG).show();
        } else {
            errorMessageCard.setVisibility(View.VISIBLE);
            loadingAnim.setVisibility(View.GONE);
            errorText.setText("Please check your internet connection and try again.");
        }

        retryCard.setOnClickListener(v -> {
            errorMessageCard.setVisibility(View.GONE);
            if (checkInternetConnection()) {
                if(selectedChipData.size() != 0){
                    String allConnectorTypes = "";
                    for (int i = 0; i < selectedChipData.size(); i++) {
                        if (i == selectedChipData.size() - 1)
                            allConnectorTypes += selectedChipData.get(i);
                        else
                            allConnectorTypes += selectedChipData.get(i) + ",";
                    }

                    getEvStations("AND", allConnectorTypes);
                }else{
                    getEvStations("OR", "NEMA1450,NEMA515,NEMA520,J1772,J1772COMBO,CHADEMO,TESLA");
                }
                //Toast.makeText(getContext(), "Good", Toast.LENGTH_LONG).show();
            } else {
                errorMessageCard.setVisibility(View.VISIBLE);
                loadingAnim.setVisibility(View.INVISIBLE);
                errorText.setText("Please check your internet connection and try again.");
            }
        });

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton chip, boolean isChecked) {

                if (isChecked) {
                    if (chip.getText().equals("NEMA 14-50")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.add("NEMA1450");
                    } else if (chip.getText().equals("NEMA 5-15")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.add("NEMA515");
                    } else if (chip.getText().equals("NEMA 5-20")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.add("NEMA520");
                    } else if (chip.getText().equals("J1772")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.add("J1772");
                    } else if (chip.getText().equals("CCS")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.add("J1772COMBO");
                    } else if (chip.getText().equals("CHAdeMO")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.add("CHADEMO");
                    } else if (chip.getText().equals("Tesla")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.add("TESLA");
                    }
                } else {
                    if (chip.getText().equals("NEMA 14-50")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.remove("NEMA1450");
                    } else if (chip.getText().equals("NEMA 5-15")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.remove("NEMA515");
                    } else if (chip.getText().equals("NEMA 5-20")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.remove("NEMA520");
                    } else if (chip.getText().equals("J1772")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.remove("J1772");
                    } else if (chip.getText().equals("CCS")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.remove("J1772COMBO");
                    } else if (chip.getText().equals("CHAdeMO")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.remove("CHADEMO");
                    } else if (chip.getText().equals("Tesla")) {
                        topLinearContainer.removeAllViews();
                        selectedChipData.remove("TESLA");
                    }
                }

                String allConnectorTypes = "";
                for (int i = 0; i < selectedChipData.size(); i++) {
                    if (i == selectedChipData.size() - 1)
                        allConnectorTypes += selectedChipData.get(i);
                    else
                        allConnectorTypes += selectedChipData.get(i) + ",";
                }

                getEvStations("AND", allConnectorTypes);

            }
        };

        c1.setOnCheckedChangeListener(checkedChangeListener);
        c2.setOnCheckedChangeListener(checkedChangeListener);
        c3.setOnCheckedChangeListener(checkedChangeListener);
        c4.setOnCheckedChangeListener(checkedChangeListener);
        c5.setOnCheckedChangeListener(checkedChangeListener);
        c6.setOnCheckedChangeListener(checkedChangeListener);
        c7.setOnCheckedChangeListener(checkedChangeListener);

        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void addEVDetailsCard(String StationName, String StationAddress, String FuelType, int LVL_1_EVSE, int LVL_2_EVSE, int DC_FAST_EVSE,
                                  String[] ConnectorTypes, String Price, String Phone, String LocationLink, double latitude, double longitude, String Status) {

        Handler handler;
        //Runnable handlerTask;

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingAnim.setVisibility(View.GONE);
                errorMessageCard.setVisibility(View.GONE);
                View view1 = View.inflate(getContext(), R.layout.ev_station_details_card, null);

                TextView stationNameTextV = view1.findViewById(R.id.station_name);
                TextView stationAddressTextV = view1.findViewById(R.id.station_address);
                TextView fuelTypeTextV = view1.findViewById(R.id.fuel_type);
                TextView level_1_evse_ports = view1.findViewById(R.id.lvl_1_evse_ports);
                TextView level_2_evse_ports = view1.findViewById(R.id.lvl_2_evse_ports);
                TextView dc_fast_evse_ports = view1.findViewById(R.id.dc_fast_evse_ports);
                ChipGroup connectorTypesChipGroup = view1.findViewById(R.id.connector_types_chip_layout);
                TextView priceTextV = view1.findViewById(R.id.price_amount);
                TextView statusTextV = view1.findViewById(R.id.status);
                CardView phoneCardV = view1.findViewById(R.id.phone_card);
                CardView locationCardV = view1.findViewById(R.id.location_card);
                ImageView fuelTypeImage = view1.findViewById(R.id.fuel_type_image);
                ImageView teslaImage = view1.findViewById(R.id.tesla_image_icon);


                stationNameTextV.setText(StationName);
                stationAddressTextV.setText(StationAddress);
                if (FuelType.equals("ELEC")) {
                    fuelTypeTextV.setText("Electric");
                    fuelTypeImage.setImageDrawable(getContext().getDrawable(R.drawable.charging_station_icon));
                } else
                    fuelTypeTextV.setText(FuelType);
                level_1_evse_ports.setText(LVL_1_EVSE + "");
                level_2_evse_ports.setText(LVL_2_EVSE + "");
                dc_fast_evse_ports.setText(DC_FAST_EVSE + "");
                boolean isTeslaAvailable = false;
                //NEMA1450 (NEMA 14-50), NEMA515 (NEMA 5-15), NEMA520 (NEMA 5-20), J1772 (J1772), J1772COMBO (CCS), CHADEMO (CHAdeMO), TESLA (Tesla)
                for (int i = 0; i < ConnectorTypes.length; i++) {
                    Chip chip = new Chip(getContext());
                    if (ConnectorTypes[i].equals("NEMA1450"))
                        chip.setText("NEMA 14-50");
                    else if (ConnectorTypes[i].equals("NEMA515"))
                        chip.setText("NEMA 5-15");
                    else if (ConnectorTypes[i].equals("NEMA520"))
                        chip.setText("NEMA 5-20");
                    else if (ConnectorTypes[i].equals("J1772"))
                        chip.setText("J1772");
                    else if (ConnectorTypes[i].equals("J1772COMBO"))
                        chip.setText("CCS");
                    else if (ConnectorTypes[i].equals("CHADEMO"))
                        chip.setText("CHAdeMO");
                    else if (ConnectorTypes[i].equals("TESLA")) {
                        chip.setText("Tesla");
                        isTeslaAvailable = true;

                    } else
                        chip.setText(ConnectorTypes[i]);
                    chip.setBackgroundColor(R.color.black);
                    chip.setChipCornerRadius(6);
                    connectorTypesChipGroup.addView(chip);
                }

                if (isTeslaAvailable) {
                    teslaImage.setVisibility(View.VISIBLE);
                }


                if (!Price.equals("null"))
                    priceTextV.setText(Price);

                if (Status.equals("E")) {
                    statusTextV.setTextColor(Color.parseColor("#246928"));
                    statusTextV.setText("Available");
                } else {
                    statusTextV.setTextColor(Color.parseColor("#D50000"));
                    statusTextV.setText("Temporarily Unavailable");
                }

                phoneCardV.setOnClickListener(v -> {
                    if (!Phone.equals("null")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + Phone));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Contact number not provided.", Toast.LENGTH_SHORT).show();
                    }
                });
                locationCardV.setOnClickListener(v -> {
                    String url = "https://www.latlong.net/c/?lat=" + latitude + "&long=" + longitude;
                    Intent intent = new Intent(getContext(), WebView.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
                });

                topLinearContainer.addView(view1);
            }
        }, 100);


    }


    private void getEvStations(String ConnectorOperator, String ConnectorType) {
        String link = "https://developer.nrel.gov/api/alt-fuel-stations/v1.json?" + "fuel_type=" + "ELEC" + "&" + "country=" + "US" + "&" +
                "limit=" + "200" + "&" + "api_key=" + "z81ByCNp5MiukKlDiOLahKLWUhUdplalf1EMFhwb" + "&" + "access=" + "public" + "&" + "status=" + "E,T" + "&" +
                "ev_connector_type_operator=" + ConnectorOperator + "&" + "ev_connector_type=" + ConnectorType;

        new fetchData(link).start();
        //"state=" + "CA" +
    }

    class fetchData extends Thread {

        String data = "";
        String link = "";


        fetchData(String link) {
            this.link = link;
        }

        @Override
        public void run() {

            Handler handler;
            //Runnable handlerTask;

            handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    loadingAnim.setVisibility(View.VISIBLE);
                    errorMessageCard.setVisibility(View.INVISIBLE);

                }
            }, 100);

            try {
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    data = data + line;

                }

                if (!data.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray dataArr = jsonObject.getJSONArray("fuel_stations");
                    //int totalResults = jsonObject.getInt("total_results");
                    Log.d("JsonData", data);

                    /**
                     * Response Fields
                     *
                     * total_results(integer)
                     * station_locator_url (String)
                     * fuel_type_code(string) -> CNG, ELEC, LPG, BD, E85, HY, LNG
                     * station_name(string)
                     * street_address(string)
                     * intersection_directions(string)
                     * city(string)
                     * state(string)
                     * zip(string)
                     * station_phone(string)
                     * status_code(string) -> E (Available), P (Planned), T (Temporarily Unavailable)
                     * access_code(string) -> public, private
                     * ev_level1_evse_num(integer) -> (Number of Level 1 EVSE Ports)
                     * ev_level2_evse_num(integer) -> (Number of Level 2 EVSE Ports)
                     * ev_dc_fast_num(integer) -> (Number of DC Fast EVSE Ports)
                     * ev_pricing(String)
                     * latitude(decimal)
                     * longitude(decimal)
                     * ev_connector_types(string) -> NEMA1450 (NEMA 14-50), NEMA515 (NEMA 5-15), NEMA520 (NEMA 5-20), J1772 (J1772), J1772COMBO (CCS), CHADEMO (CHAdeMO), TESLA (Tesla)
                     *
                     */

                    //Runnable handlerTask;

                    handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dataArr.length() == 0) {
                                loadingAnim.setVisibility(View.GONE);
                                errorMessageCard.setVisibility(View.VISIBLE);
                                errorText.setText("No Charging Stations found at your location.");
                            } else {
                                loadingAnim.setVisibility(View.INVISIBLE);
                                errorMessageCard.setVisibility(View.INVISIBLE);
                                controls.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 100);

                    for (int i = 0; i < dataArr.length(); i++) {

                        JSONObject dataObj = dataArr.getJSONObject(i);

                        // These values can be null

                        int level1Port = 0;
                        int level2Port = 0;
                        int dcFast = 0;

                        try {
                            level1Port = dataObj.getInt("ev_level1_evse_num");
                        } catch (Exception e) {
                        }
                        try {
                            level2Port = dataObj.getInt("ev_level2_evse_num");
                        } catch (Exception e) {
                        }
                        try {
                            dcFast = dataObj.getInt("ev_dc_fast_num");
                        } catch (JSONException e) {
                        }

                        String stationName = dataObj.getString("station_name");
                        String stationAddress = dataObj.getString("street_address");
                        String fuelType = dataObj.getString("fuel_type_code");
                        //String locationUrl = dataObj.getString("station_locator_url");
                        String phone = dataObj.getString("station_phone");
                        String status = dataObj.getString("status_code");

                        String pricing = dataObj.getString("ev_pricing");
                        double latitude = dataObj.getDouble("latitude");
                        double longitude = dataObj.getDouble("longitude");
                        Log.d("JsonDataTypes", dataObj.getString("ev_connector_types"));
                        JSONArray connectorType = dataObj.getJSONArray("ev_connector_types");
                        String[] connectorTypeArr = new String[connectorType.length()];
                        for (int j = 0; j < connectorType.length(); j++) {
                            connectorTypeArr[j] = connectorType.getString(j);
                        }

                        addEVDetailsCard(stationName, stationAddress, fuelType, level1Port, level2Port, dcFast, connectorTypeArr, pricing, phone, null,
                                latitude, longitude, status);
                        // Do with data.

                    }

                } else {
                    //Runnable handlerTask;

                    handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingAnim.setVisibility(View.GONE);
                            errorMessageCard.setVisibility(View.VISIBLE);
                            errorText.setText("No Charging Stations found at your location.");
                        }
                    }, 100);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("JsonData", e.getMessage());
            }


        }

    }

    private boolean checkInternetConnection() {
        loadingAnim.setVisibility(View.VISIBLE);
        errorMessageCard.setVisibility(View.GONE);
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

}















