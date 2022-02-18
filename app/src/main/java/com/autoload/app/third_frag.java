package com.autoload.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    SharedPreferences VehicleDetails, TopCompaniesLink, NewLaunchesDetails;

    SliderView TopSlider;
    CardView first_card, sec_card, third_card, fourth_card, fifth_card, sixth_card;
    ImageView NLImg1, NLImg2, NLImg3, NLImg4, NLImg5, NLImg6;
    TextView NLTxt1, NLTxt2, NLTxt3, NLTxt4, NLTxt5, NLTxt6;

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
        TopCompaniesLink = getContext().getSharedPreferences("TopCompaniesLink", Context.MODE_PRIVATE);
        NewLaunchesDetails = getContext().getSharedPreferences("NewLaunchesDetails", Context.MODE_PRIVATE);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bitmap[] images = {StringToBitMap(VehicleDetails.getString("i1", null)), StringToBitMap(VehicleDetails.getString("i2", null)),
                StringToBitMap(VehicleDetails.getString("i3", null)), StringToBitMap(VehicleDetails.getString("i4", null))};

        String[] names = {VehicleDetails.getString("n1", ""), VehicleDetails.getString("n2", ""), VehicleDetails.getString("n3", ""),
                VehicleDetails.getString("n4", "")};

        String[] descriptions = {VehicleDetails.getString("d1", ""), VehicleDetails.getString("d2", ""), VehicleDetails.getString("d3", ""),
                VehicleDetails.getString("d4", "")};

        String[] links = {VehicleDetails.getString("l1", ""), VehicleDetails.getString("l2", ""), VehicleDetails.getString("l3", ""),
                VehicleDetails.getString("l4", "")};

        setAutoScrollAdapter(images, names, descriptions, links);

        setNewLaunches();

        topCompaniesCardListeners();

        newLaunchesListeners();
    }

    private void hook() {
        TopSlider = view.findViewById(R.id.vehicle_slider);

        first_card = view.findViewById(R.id.first_card);
        sec_card = view.findViewById(R.id.second_card);
        third_card = view.findViewById(R.id.third_card);
        fourth_card = view.findViewById(R.id.fourth_card);
        fifth_card = view.findViewById(R.id.fifth_card);
        sixth_card = view.findViewById(R.id.sixth_card);

        NLImg1 = view.findViewById(R.id.new_launch_img1);
        NLImg2 = view.findViewById(R.id.new_launch_img2);
        NLImg3 = view.findViewById(R.id.new_launch_img3);
        NLImg4 = view.findViewById(R.id.new_launch_img4);
        NLImg5 = view.findViewById(R.id.new_launch_img5);
        NLImg6 = view.findViewById(R.id.new_launch_img6);

        NLTxt1 = view.findViewById(R.id.new_launch_text1);
        NLTxt2 = view.findViewById(R.id.new_launch_text2);
        NLTxt3 = view.findViewById(R.id.new_launch_text3);
        NLTxt4 = view.findViewById(R.id.new_launch_text4);
        NLTxt5 = view.findViewById(R.id.new_launch_text5);
        NLTxt6 = view.findViewById(R.id.new_launch_text6);
    }

    private void setNewLaunches() {

        if(NewLaunchesDetails.contains("i1") && NewLaunchesDetails.contains("t1")){
            NLImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            NLImg1.setImageBitmap(StringToBitMap(NewLaunchesDetails.getString("i1", null)));
            NLTxt1.setText(NewLaunchesDetails.getString("t1", ""));
        }

        if(NewLaunchesDetails.contains("i2") && NewLaunchesDetails.contains("t2")){
            NLImg2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            NLImg2.setImageBitmap(StringToBitMap(NewLaunchesDetails.getString("i2", null)));
            NLTxt2.setText(NewLaunchesDetails.getString("t2", ""));
        }

        if(NewLaunchesDetails.contains("i3") && NewLaunchesDetails.contains("t3")){
            NLImg3.setScaleType(ImageView.ScaleType.CENTER_CROP);
            NLImg3.setImageBitmap(StringToBitMap(NewLaunchesDetails.getString("i3", null)));
            NLTxt3.setText(NewLaunchesDetails.getString("t3", ""));
        }

        if(NewLaunchesDetails.contains("i4") && NewLaunchesDetails.contains("t4")){
            NLImg4.setScaleType(ImageView.ScaleType.CENTER_CROP);
            NLImg4.setImageBitmap(StringToBitMap(NewLaunchesDetails.getString("i4", null)));
            NLTxt4.setText(NewLaunchesDetails.getString("t4", ""));
        }

        if(NewLaunchesDetails.contains("i5") && NewLaunchesDetails.contains("t5")){
            NLImg5.setScaleType(ImageView.ScaleType.CENTER_CROP);
            NLImg5.setImageBitmap(StringToBitMap(NewLaunchesDetails.getString("i5", null)));
            NLTxt5.setText(NewLaunchesDetails.getString("t5", ""));
        }

        if(NewLaunchesDetails.contains("i6") && NewLaunchesDetails.contains("t6")){
            NLImg6.setScaleType(ImageView.ScaleType.CENTER_CROP);
            NLImg6.setImageBitmap(StringToBitMap(NewLaunchesDetails.getString("i6", null)));
            NLTxt6.setText(NewLaunchesDetails.getString("t6", ""));
        }

    }

    private void newLaunchesListeners(){

        NLImg1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", NewLaunchesDetails.getString("l1", ""));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        NLImg2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", NewLaunchesDetails.getString("l2", ""));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        NLImg3.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", NewLaunchesDetails.getString("l3", ""));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        NLImg4.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", NewLaunchesDetails.getString("l4", ""));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        NLImg5.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", NewLaunchesDetails.getString("l5", ""));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        NLImg6.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", NewLaunchesDetails.getString("l6", ""));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

    }

    private void topCompaniesCardListeners() {
        first_card.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", TopCompaniesLink.getString("link1", " "));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        sec_card.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", TopCompaniesLink.getString("link2", " "));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        third_card.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", TopCompaniesLink.getString("link3", " "));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        fourth_card.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", TopCompaniesLink.getString("link4", " "));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        fifth_card.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", TopCompaniesLink.getString("link5", " "));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        sixth_card.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", TopCompaniesLink.getString("link6", " "));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });
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




















