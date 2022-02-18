package com.autoload.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link first_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class first_frag extends Fragment {

    View view;

    SliderView TopSlider;
    CardView art1, art2, art3, art4, art5, art6;
    CardView banner1, banner2, banner3;
    ImageView bannerBottom, locationExpandImg;
    public static TextView locationTextV;

    SharedPreferences LocationSP;

    public static int tabPos = 0;

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

        hook();

        LocationSP = getContext().getSharedPreferences("Location", Context.MODE_PRIVATE);

        if(LocationSP.contains("country")){
            if(LocationSP.contains("city"))
                first_frag.locationTextV.setText(LocationSP.getString("city", "") + ", " + LocationSP.getString("country", ""));
            else
                first_frag.locationTextV.setText(LocationSP.getString("country", ""));

        }else
            first_frag.locationTextV.setText(getString(R.string.default_country));

        setAutoScrollAdapter(new Drawable[]{getContext().getDrawable(R.drawable.scroll_img_1), getContext().getDrawable(R.drawable.scroll_img_2), getContext().getDrawable(R.drawable.scroll_img_3)});

        articleListeners();

        bannersListeners();

        locationTextV.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), Location.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        locationExpandImg.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), Location.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        return view;
    }

    private void hook() {
        TopSlider = view.findViewById(R.id.image_slider);

        art1 = view.findViewById(R.id.art1);
        art2 = view.findViewById(R.id.art2);
        art3 = view.findViewById(R.id.art3);
        art4 = view.findViewById(R.id.art4);
        art5 = view.findViewById(R.id.art5);
        art6 = view.findViewById(R.id.art6);

        banner1 = view.findViewById(R.id.banner1);
        banner2 = view.findViewById(R.id.banner2);
        banner3 = view.findViewById(R.id.banner3);

        bannerBottom = view.findViewById(R.id.banner_bottom);

        locationTextV = view.findViewById(R.id.locationTextV);
        locationExpandImg = view.findViewById(R.id.expand_image);
    }

    private void bannersListeners() {

        banner1.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), connect_n_control.class);
            startActivity(intent);
            
        });

        banner2.setOnClickListener(v ->{
            tabPos = 1;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new sec_frag()).commit();
            HomePage.bottom_nav.setSelectedItemId(R.id.sec_frag);
        });

        banner3.setOnClickListener(v ->{

        });

        bannerBottom.setOnClickListener(v ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new third_frag()).commit();
            HomePage.bottom_nav.setSelectedItemId(R.id.third_frag);
        });
    }

    private void articleListeners(){
        art1.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", getContext().getString(R.string.article1link));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        art2.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", getContext().getString(R.string.article2link));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        art3.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", getContext().getString(R.string.article3link));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        art4.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", getContext().getString(R.string.article4link));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        art5.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", getContext().getString(R.string.article5link));
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });

        art6.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), WebView.class);
            intent.putExtra("url", "https://www.bloomberg.com/opinion/articles/2021–11-04/electric-cars-aren-t-as-green-as-you-might-think?utm_medium=cpc_search&utm_campaign=NB_ACQ_DSAXX_DSATESTTCPAXX_EVG_XXXX_XXX_COALL_EN_EN_X_BLOM_GO_SE_XXX_XXXXXXXXXX&gclid=CjwKCAiA9aKQBhBREiwAyGP5lfx_rkEwXov7AE8QN5OJwMiIDwwfSVBs6Pj4FNAsAL4BQPWoyqTG9BoCJ-UQAvD_BwE&gclsrc=aw.ds");
            startActivity(intent);
            getActivity().overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        });


    }

    private void setAutoScrollAdapter(Drawable[] images) {
        AutoScrollAdapter autoScrollAdapter = new AutoScrollAdapter(images, getActivity());
        TopSlider.setSliderAdapter(autoScrollAdapter);
        TopSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        TopSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        TopSlider.startAutoCycle();
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