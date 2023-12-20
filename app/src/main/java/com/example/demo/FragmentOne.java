package com.example.demo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


public class FragmentOne extends Fragment {

    public FragmentOne() {
        super(R.layout.fragment_one);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(savedInstanceState == null)
        {
            transaction.add(R.id._container,FragmentLeft.class,null)
                    .setReorderingAllowed(true)
                    .commit();
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               if(tab.getPosition() == 0){
                   FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                   transaction1.replace(R.id._container, FragmentLeft.class, null)
                           .setReorderingAllowed(true)
                           .commit();
               }
               else if(tab.getPosition() == 1){
                   FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                   transaction1.replace(R.id._container, FragmentRight.class, null)
                           .setReorderingAllowed(true)
                           .commit();
               }
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