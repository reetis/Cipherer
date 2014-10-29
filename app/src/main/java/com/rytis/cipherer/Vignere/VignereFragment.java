package com.rytis.cipherer.Vignere;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rytis.cipherer.R;
import com.rytis.cipherer.widgets.SlidingTabLayout;

import java.util.ArrayList;

public class VignereFragment extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabbed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new TabsAdapter(getFragmentManager(), EncodedFragment.newInstance(),
                EncodedFragment.newInstance()));

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public static VignereFragment newInstance() {
        return new VignereFragment();
    }

    private class TabsAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<>();

        TabsAdapter(FragmentManager fm, Fragment left_tab, Fragment right_tab) {
            super(fm);
            fragments.add(left_tab);
            fragments.add(right_tab);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.vignere);
                case 1:
                    return getString(R.string.text);
                default:
                    return "Unknown tab";
            }
        }
    }
}