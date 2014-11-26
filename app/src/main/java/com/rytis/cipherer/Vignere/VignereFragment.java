package com.rytis.cipherer.Vignere;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tabbed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String initEncText = preferences.getString("VignereEncodedText", "");
        String initDecText = preferences.getString("VignereDecodedText", "");
        String initKey = preferences.getString("VignereKey", "");

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new TabsAdapter(getChildFragmentManager(),
                EncodedFragment.newInstance(initEncText, initKey),
                DecodedFragment.newInstance(initDecText, initKey));
        mViewPager.setAdapter(adapter);

        coder = new VignereCoder(initDecText, initEncText, initKey);

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
                if (fragment == null) {
                    return;
                }
                try {
                    if (i == 0) {
                        coder.encode();
                        preferences.edit().putString("VignereEncodedText", coder.getEncodedText()).apply();
                        ((EncodedFragment) fragment).setValues(coder.getEncodedText(), coder.getKey());
                    } else if (i == 1){
                        coder.decode();
                        preferences.edit().putString("VignereDecodedText", coder.getDecodedText()).apply();
                        ((DecodedFragment) fragment).setValues(coder.getDecodedText(), coder.getKey());
                    }
                } catch (NullPointerException e) {
                    Log.d("INFO", "Error setting values", e);
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
        preferences.edit().putString("VignereKey", key).apply();
        coder.setKey(key);
    }

    @Override
    public void onChangedEncodedText(String text) {
        preferences.edit().putString("VignereEncodedText", text).apply();
        coder.setEncodedText(text);
        preferences.edit().putString("VignereDecodedText", coder.getDecodedText()).apply();
    }

    @Override
    public void onChangedDecodedText(String text) {
        preferences.edit().putString("VignereDecodedText", text).apply();
        coder.setDecodedText(text);
        preferences.edit().putString("VignereEncodedText", coder.getEncodedText()).apply();
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
            encode();
        }

        public String getEncodedText() {
            return encodedText;
        }

        public void setEncodedText(String encodedText) {
            this.encodedText = encodedText;
            decode();
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
                        c = (char) ((c - 'a' + keyValues.get(i % keyValues.size())) % 26 + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        c = (char) ((c - 'A' + keyValues.get(i % keyValues.size())) % 26 + 'A');
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
                        c = (char) ((c - 'a' + 26 - keyValues.get(i % keyValues.size())) % 26 + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        c = (char) ((c - 'A' + 26 - keyValues.get(i % keyValues.size())) % 26 + 'A');
                    }
                    decoded = decoded.concat(String.valueOf(c));
                }
            }
            return decoded;
        }
    }
}