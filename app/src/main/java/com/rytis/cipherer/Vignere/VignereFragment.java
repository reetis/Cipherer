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

public class VignereFragment extends Fragment implements DecodedFragment.DecodedInteractionListener, EncodedFragment.EncodedInteractionListener {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private TabsAdapter adapter;
    private VignereCoder coder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabbed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new TabsAdapter(getChildFragmentManager(), EncodedFragment.newInstance(), DecodedFragment.newInstance());
        mViewPager.setAdapter(adapter);
        coder = new VignereCoder();

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
                    coder.encode();
                    ((EncodedFragment) fragment).setValues(coder.getEncodedText(), coder.getKey());
                } else {
                    coder.decode();
                    ((DecodedFragment) fragment).setValues(coder.getDecodedText(), coder.getKey());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public static VignereFragment newInstance() {
        return new VignereFragment();
    }

    @Override
    public void onChangedKey(String key) {
        coder.setKey(key);
    }

    @Override
    public void onChangedEncodedText(String text) {
        coder.setEncodedText(text);
    }

    @Override
    public void onChangedDecodedText(String text) {
        coder.setDecodedText(text);
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

    private class VignereCoder {
        private String decodedText;
        private String encodedText;
        private String key;

        private VignereCoder(String decodedText, String encodedText, String key) {
            this.decodedText = decodedText;
            this.encodedText = encodedText;
            this.key = key;
        }

        private VignereCoder() {
            this("", "", "");
        }

        public String getDecodedText() {
            return decodedText;
        }

        public void setDecodedText(String decodedText) {
            this.decodedText = decodedText;
            //encode();
        }

        public String getEncodedText() {
            return encodedText;
        }

        public void setEncodedText(String encodedText) {
            this.encodedText = encodedText;
            //decode();
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void encode() {
            this.encodedText = encode(this.decodedText, this.key);
        }

        public void decode() {
            this.decodedText = decode(this.encodedText, this.key);
        }

        public String encode(String text, String key) {
            String encoded = "";
            if (key.isEmpty()){
                encoded = text;
            } else {
                key = key.toLowerCase();
                ArrayList<Integer> keyValues = new ArrayList<>();
                for (int i = 0; i < key.length(); ++i) {
                    char c = key.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        keyValues.add( c - 'a' );
                    } else {
                        keyValues.add(0);
                    }
                }
                for (int i = 0; i < text.length(); ++i) {
                    char c = text.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        c = (char) ((c - 'a' + keyValues.get(i % keyValues.size())) % 26);
                    } else if (c >= 'A' && c <= 'Z') {
                        c = (char) ((c - 'A' + keyValues.get(i % keyValues.size())) % 26);
                    }
                    encoded = encoded.concat(String.valueOf(c));
                }
            }
            return encoded;
        }

        private String decode(String text, String key) {
            String decoded = "";
            if (key.isEmpty()){
                decoded = text;
            } else {
                key = key.toLowerCase();
                ArrayList<Integer> keyValues = new ArrayList<>();
                for (int i = 0; i < key.length(); ++i) {
                    char c = key.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        keyValues.add( c - 'a' );
                    } else {
                        keyValues.add(0);
                    }
                }
                for (int i = 0; i < text.length(); ++i) {
                    char c = text.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        c = (char) ((c - 'a' + 26 - keyValues.get(i % keyValues.size())) % 26);
                    } else if (c >= 'A' && c <= 'Z') {
                        c = (char) ((c - 'A' + 26 - keyValues.get(i % keyValues.size())) % 26);
                    }
                    decoded = decoded.concat(String.valueOf(c));
                }
            }
            return decoded;
        }
    }
}