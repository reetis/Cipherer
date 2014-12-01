package com.rytis.cipherer.Braille;

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

public class BrailleFragment extends Fragment implements DecodedFragment.DecodedInteractionListener, EncodedFragment.EncodedInteractionListener {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private TabsAdapter adapter;
    private BrailleCoder coder;

    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabbed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String initEncText = preferences.getString("BrailleEncodedText", "");
        String initDecText = preferences.getString("BrailleDecodedText", "");
        
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new TabsAdapter(getChildFragmentManager(),
                EncodedFragment.newInstance(initEncText),
                DecodedFragment.newInstance(initDecText));
        mViewPager.setAdapter(adapter);
        coder = new BrailleCoder(initDecText, initEncText);

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
                    coder.decode();
                    preferences.edit().putString("BrailleDecodedText", coder.getDecodedText()).apply();
                    ((DecodedFragment) fragment).setValues(coder.getDecodedText());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public static BrailleFragment newInstance() {
        return new BrailleFragment();
    }

    @Override
    public void onChangedEncodedText(String text) {
        preferences.edit().putString("BrailleEncodedText", text).apply();
        coder.setEncodedText(text);
//        preferences.edit().putString("BrailleDecodedText", coder.getDecodedText()).apply();
    }

    @Override
    public void onChangedDecodedText(String text) {
        preferences.edit().putString("BrailleDecodedText", text).apply();
        coder.setDecodedText(text);
        preferences.edit().putString("BrailleEncodedText", coder.getEncodedText()).apply();
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
                    return getString(R.string.braille);
                case 1:
                    return getString(R.string.text);
                default:
                    return "Unknown tab";
            }
        }
    }

    private class BrailleCoder {
        private String decodedText;
        private String encodedText;

        private BrailleCoder(String decodedText, String encodedText) {
            this.decodedText = decodedText;
            this.encodedText = encodedText;
        }

        private BrailleCoder() {
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
            int newC;
            text = text.toLowerCase();
            for(int i = 0; i < text.length(); ++i) {
                c = text.charAt(i);
                switch (c) {
                    case 'a':
                        newC = 0b1;
                        break;
                    case 'b':
                        newC = 0b11;
                        break;
                    case 'c':
                        newC = 0b1001;
                        break;
                    case 'd':
                        newC = 0b11001;
                        break;
                    case 'e':
                        newC = 0b10001;
                        break;
                    case 'f':
                        newC = 0b1011;
                        break;
                    case 'g':
                        newC = 0b11011;
                        break;
                    case 'h':
                        newC = 0b10011;
                        break;
                    case 'i':
                        newC = 0b1010;
                        break;
                    case 'j':
                        newC = 0b11010;
                        break;
                    case 'k':
                        newC = 0b101;
                        break;
                    case 'l':
                        newC = 0b111;
                        break;
                    case 'm':
                        newC = 0b1101;
                        break;
                    case 'n':
                        newC = 0b11101;
                        break;
                    case 'o':
                        newC = 0b10101;
                        break;
                    case 'p':
                        newC = 0b1111;
                        break;
                    case 'q':
                        newC = 0b11111;
                        break;
                    case 'r':
                        newC = 0b10111;
                        break;
                    case 's':
                        newC = 0b1110;
                        break;
                    case 't':
                        newC = 0b11110;
                        break;
                    case 'u':
                        newC = 0b100101;
                        break;
                    case 'v':
                        newC = 0b100111;
                        break;
                    case 'w':
                        newC = 0b111010;
                        break;
                    case 'x':
                        newC = 0b101101;
                        break;
                    case 'y':
                        newC = 0b111101;
                        break;
                    case 'z':
                        newC = 0b110101;
                        break;
                    case '!':
                        newC = 0b10110;
                        break;
                    case '\'':
                        newC = 0b100;
                        break;
                    case ',':
                        newC = 0b10;
                        break;
                    case '-':
                        newC = 0b100100;
                        break;
                    case '.':
                        newC = 0b110010;
                        break;
                    case '?':
                        newC = 0b100110;
                        break;
                    default:
                        newC = ' ';
                }
                encoded = encoded + (char) (newC + 10240);
            }
            return encoded;
        }

        private String decode(String text){
            Log.d("INFO", "Got " + text);
            if (text.isEmpty()) {
                return "";
            }
            String decoded = "";
            int c;
            char newC;
            for(int i = 0; i < text.length(); ++i) {
                c = text.charAt(i) - 10240;
                Log.d("INFO", text.charAt(i) + " is " + c);
                switch (c) {
                    case 0b1:
                        newC = 'a';
                        break;
                    case 0b11:
                        newC = 'b';
                        break;
                    case 0b1001:
                        newC = 'c';
                        break;
                    case 0b11001:
                        newC = 'd';
                        break;
                    case 0b10001:
                        newC = 'e';
                        break;
                    case 0b1011:
                        newC = 'f';
                        break;
                    case 0b11011:
                        newC = 'g';
                        break;
                    case 0b10011:
                        newC = 'h';
                        break;
                    case 0b1010:
                        newC = 'i';
                        break;
                    case 0b11010:
                        newC = 'j';
                        break;
                    case 0b101:
                        newC = 'k';
                        break;
                    case 0b111:
                        newC = 'l';
                        break;
                    case 0b1101:
                        newC = 'm';
                        break;
                    case 0b11101:
                        newC = 'n';
                        break;
                    case 0b10101:
                        newC = 'o';
                        break;
                    case 0b1111:
                        newC = 'p';
                        break;
                    case 0b11111:
                        newC = 'q';
                        break;
                    case 0b10111:
                        newC = 'r';
                        break;
                    case 0b1110:
                        newC = 's';
                        break;
                    case 0b11110:
                        newC = 't';
                        break;
                    case 0b100101:
                        newC = 'u';
                        break;
                    case 0b100111:
                        newC = 'v';
                        break;
                    case 0b111010:
                        newC = 'w';
                        break;
                    case 0b101101:
                        newC = 'x';
                        break;
                    case 0b111101:
                        newC = 'y';
                        break;
                    case 0b110101:
                        newC = 'z';
                        break;
                    case 0b10110:
                        newC = '!';
                        break;
                    case 0b100:
                        newC = '\'';
                        break;
                    case 0b10:
                        newC = ',';
                        break;
                    case 0b100100:
                        newC = '-';
                        break;
                    case 0b110010:
                        newC = '.';
                        break;
                    case 0b100110:
                        newC = '?';
                        break;
                    default:
                        newC = ' ';
                }
                decoded = decoded + newC;
            }
            Log.d("INFO", "Out " + decoded);
            return decoded;
        }
    }
}