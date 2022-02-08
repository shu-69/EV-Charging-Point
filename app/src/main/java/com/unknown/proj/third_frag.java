package com.unknown.proj;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link third_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class third_frag extends Fragment {

    View view;
    SharedPreferences VehicleDetails;

    SliderView TopSlider;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public third_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment third_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static third_frag newInstance(String param1, String param2) {
        third_frag fragment = new third_frag();
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
        view = inflater.inflate(R.layout.fragment_third_frag, container, false);

        hook();

        VehicleDetails = getContext().getSharedPreferences("VehicleDetails", Context.MODE_PRIVATE);

        Bitmap[] images = {StringToBitMap(VehicleDetails.getString("i1", null)), StringToBitMap(VehicleDetails.getString("i2", null)),
                StringToBitMap(VehicleDetails.getString("i3", null)), StringToBitMap(VehicleDetails.getString("i4", null))};

        String[] names = {VehicleDetails.getString("n1", ""), VehicleDetails.getString("n2", ""), VehicleDetails.getString("n3", ""),
                VehicleDetails.getString("n4", "")};

        String[] descriptions = {VehicleDetails.getString("d1", ""), VehicleDetails.getString("d2", ""), VehicleDetails.getString("d3", ""),
                VehicleDetails.getString("d4", "")};

        String[] links = {VehicleDetails.getString("l1", ""), VehicleDetails.getString("l2", ""), VehicleDetails.getString("l3", ""),
                VehicleDetails.getString("l4", "")};

        setAutoScrollAdapter(images, names, descriptions, links);

        return view;
    }

    private void hook() {
        TopSlider = view.findViewById(R.id.vehicle_slider);
    }

    public String BitMapToString(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void setAutoScrollAdapter(Bitmap[] images, String[] names, String[] descriptions, String[] links) {

        VehicleDetailsAutoScrollAdapter adapter = new VehicleDetailsAutoScrollAdapter(images, names, descriptions, links);
        TopSlider.setSliderAdapter(adapter);
        TopSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        TopSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        TopSlider.startAutoCycle();
    }
}




















