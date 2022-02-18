package com.autoload.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sec_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sec_frag extends Fragment {

    View view;

    public static TabLayout tabLayout;

    Handler mainHandler = new Handler();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sec_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sec_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static sec_frag newInstance(String param1, String param2) {
        sec_frag fragment = new sec_frag();
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
        view =  inflater.inflate(R.layout.fragment_sec_frag, container, false);

        tabLayout = view.findViewById(R.id.sec_frag_tab_layout);


//        if(first_frag.viewCount >= 3){
//            first_frag.tabPos = 0;
//            first_frag.viewCount = 0 ;
//        }

        try {
            tabLayout.getTabAt(first_frag.tabPos).select();
            if(first_frag.tabPos == 1)
            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sec_frag_fragmentContainerView, new MapFragment()).commit();
            }
            else
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sec_frag_fragmentContainerView, new EvStationsListFragment()).commit();
        }catch (Exception e){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sec_frag_fragmentContainerView, new EvStationsListFragment()).commit();
        }

        //first_frag.tabPos = 0;

//        if(tabLayout.getSelectedTabPosition() == 0){
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sec_frag_fragmentContainerView, new EvStationsListFragment()).commit();
//        }else if(tabLayout.getSelectedTabPosition() == 1){
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sec_frag_fragmentContainerView, new MapFragment()).commit();
//        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try{
                    if(tabLayout.getSelectedTabPosition() == 0){
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sec_frag_fragmentContainerView, new EvStationsListFragment()).commit();
                    }else if(tabLayout.getSelectedTabPosition() == 1){
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sec_frag_fragmentContainerView, new MapFragment()).commit();
                    }
                }catch (Exception e){}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}















