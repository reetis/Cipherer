package com.rytis.cipherer.ROT;

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

public class ROTFragment extends Fragment implements DecodedFragment.DecodedInteractionListener, EncodedFragment.EncodedInteractionListener {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private TabsAdapter adapter;
    private ROTCoder coder;

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
        coder = new ROTCoder();

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

    public static ROTFragment newInstance() {
        return new ROTFragment();
    }

    @Override
    public void onChangedKey(int key) {
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
                    return getString(R.string.rot);
                case 1:
                    return getString(R.string.text);
                default:
                    return "Unknown tab";
            }
        }
    }

    private class ROTCoder {
        private String decodedText;
        private String encodedText;
        private int key;

        private ROTCoder(String decodedText, String encodedText, int key) {
            this.decodedText = decodedText;
            this.encodedText = encodedText;
            this.key = key;
        }

        private ROTCoder() {
            this("", "", 0);
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

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void encode() {
            this.encodedText = encode(this.decodedText, this.key);
        }

        public void decode() {
            this.decodedText = decode(this.encodedText, this.key);
        }

        public String encode(String text, int key) {
            String encoded = "";

            for (int i = 0; i < text.length(); ++i) {
                char c = text.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    c = (char) ((c - 'a' + key) % 26 + 'a');
                } else if (c >= 'A' && c <= 'Z') {
                    c = (char) ((c - 'A' + key) % 26 + 'A');
                }
                encoded = encoded.concat(String.valueOf(c));
            }

            return encoded;
        }

        private String decode(String text, int key) {
            String decoded = "";

            for (int i = 0; i < text.length(); ++i) {
                char c = text.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    c = (char) ((c - 'a' + 26 - key % 26) % 26 + 'a');
                } else if (c >= 'A' && c <= 'Z') {
                    c = (char) ((c - 'A' + 26 - key % 26) % 26 + 'A');
                }
                decoded = decoded.concat(String.valueOf(c));
            }

            return decoded;
        }
    }
}