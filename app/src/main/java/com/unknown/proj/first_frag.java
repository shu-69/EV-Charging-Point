package com.unknown.proj;

import android.net.http.Request;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kong.unirest.Unirest;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link first_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class first_frag extends Fragment {

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public first_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment first_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static first_frag newInstance(String param1, String param2) {
        first_frag fragment = new first_frag();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_frag, container, false);


        // TODO :::::::::::::: Remove fuelPrice class

        return view;
    }

    class fuelPrices extends Thread {
        public void run() {

            try {

                HttpResponse response = (HttpResponse) Unirest.get("https://api.collectapi.com/gasPrice/otherCountriesDiesel")
                        .header("content-type", "application/json")
                        .header("authorization", "apikey 662ofCBZ7XFY3Qvc42K3vU:7ytqBpUdG9yG1KFUeukM46")
                        .asString();

                String result;



                HttpEntity entity = response.getEntity();


                if (entity != null) {

                    // A Simple JSON Response Read
                    InputStream instream = entity.getContent();
                    result = convertStreamToString(instream);
                    // now you have the string representation of the HTML request
                    System.out.println("RESPONSE: " + result);
                    instream.close();
//                    if (response.getStatusLine().getStatusCode() == 200) {
//                        netState.setLogginDone(true);
//                    }

                }
                // Headers
                org.apache.http.Header[] headers = response.getAllHeaders();
                for (int i = 0; i < headers.length; i++) {
                    System.out.println(headers[i]);
                }
            } catch (Exception e) {
                System.out.println("Ex" + e.getMessage());
            }


        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}