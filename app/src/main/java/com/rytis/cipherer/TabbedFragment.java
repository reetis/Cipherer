package com.rytis.cipherer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabbedFragment extends Fragment {

    private FragmentTabHost mTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.content_frame);

        mTabHost.addTab(mTabHost.newTabSpec("TabA").setIndicator("TabA"), VignereFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("TabB").setIndicator("TabB"), VignereFragment.class, null);

        return mTabHost;
    }

    public static TabbedFragment newInstance() {
        return new TabbedFragment();
    }

    public static class VignereFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_vignere_cipher, container, false);
        }
    }
}