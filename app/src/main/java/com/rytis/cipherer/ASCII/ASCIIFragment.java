package com.rytis.cipherer.ASCII;

import android.content.Context;
import android.content.SharedPreferences;
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

public class ASCIIFragment extends Fragment implements DecodedFragment.DecodedInteractionListener, EncodedFragment.EncodedInteractionListener {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private TabsAdapter adapter;
    private ASCIICoder coder;

    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabbed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String initEncText = preferences.getString("ASCIIEncodedText", "");
        String initDecText = preferences.getString("ASCIIDecodedText", "");
        
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new TabsAdapter(getChildFragmentManager(),
                EncodedFragment.newInstance(initEncText),
                DecodedFragment.newInstance(initDecText));
        mViewPager.setAdapter(adapter);
        coder = new ASCIICoder(initDecText, initEncText);

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Fragment fragment = adapter.getItem(i);
                if (i == 0) {
                    if (!((EncodedFragment) fragment).getText().equals(coder.getEncodedText())) {
                        ((EncodedFragment) fragment).setValues(coder.getEncodedText());
                    }
                } else {
                    if (!((DecodedFragment) fragment).getText().equals(coder.getDecodedText())) {
                        ((DecodedFragment) fragment).setValues(coder.getDecodedText());
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public static ASCIIFragment newInstance() {
        return new ASCIIFragment();
    }

    @Override
    public void onChangedEncodedText(String text) {
        preferences.edit().putString("ASCIIEncodedText", text).apply();
        coder.setEncodedText(text);
        preferences.edit().putString("ASCIIDecodedText", coder.getDecodedText()).apply();
    }

    @Override
    public void onChangedDecodedText(String text) {
        preferences.edit().putString("ASCIIDecodedText", text).apply();
        coder.setDecodedText(text);
        preferences.edit().putString("ASCIIEncodedText", coder.getEncodedText()).apply();
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
                    return getString(R.string.ascii);
                case 1:
                    return getString(R.string.text);
                default:
                    return "Unknown tab";
            }
        }
    }

    private class ASCIICoder {
        private String decodedText;
        private String encodedText;

        private ASCIICoder(String decodedText, String encodedText) {
            this.decodedText = decodedText;
            this.encodedText = encodedText;
        }

        private ASCIICoder() {
            this("", "");
        }

        public String getDecodedText() {
            return decodedText;
        }

        public void setDecodedText(String decodedText) {
            this.decodedText = decodedText;
            encode();
        }

        public String getEncodedText() {
            return encodedText;
        }

        public void setEncodedText(String encodedText) {
            this.encodedText = encodedText;
            decode();
        }

        public void encode() {
            this.encodedText = encode(this.decodedText);
        }

        public void decode() {
            this.decodedText = decode(this.encodedText);
        }

        public String encode(String text) {
            String encoded = "";
//            text = text.trim();
            char c;
            for(int i = 0; i < text.length(); ++i) {
                c = text.charAt(i);
                encoded = encoded.concat(Integer.toString((int)c) + " ");
            }
            return encoded;
        }

        private String decode(String text){
            if (text.isEmpty()) {
                return "";
            }
            String decoded = "";
            text = text.trim();
            String[] numbers = text.split("\\s+");
            int num;
            try {
                for (String n : numbers) {
                    num = Integer.valueOf(n);
                    decoded = decoded + (char) num;
                }
            } catch (NumberFormatException e) {
                decoded = "";
                throw e;
            }
            return decoded;
        }
    }
}