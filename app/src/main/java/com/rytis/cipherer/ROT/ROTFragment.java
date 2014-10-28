package com.rytis.cipherer.ROT;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rytis.cipherer.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ROTFragment extends Fragment {


    public ROTFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rot, container, false);
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();

        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab1 = actionBar.newTab()
                .setText("TAB1")
                .setTabListener(new ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    }
                    @Override
                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    }
                    @Override
                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    }
                });
        actionBar.addTab(tab1);

        ActionBar.Tab tab2 = actionBar.newTab()
                .setText("TAB2")
                .setTabListener(new ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    }
                    @Override
                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    }
                    @Override
                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    }
                });
        actionBar.addTab(tab2);


        return view;
    }

    public static ROTFragment newInstance(){
        return new ROTFragment();
    }

}
