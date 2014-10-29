package com.rytis.cipherer.Vignere;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rytis.cipherer.R;

/**
* Created by rytis on 14.10.29.
*/
class EncodedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vignere_cipher, container, false);
        return view;
    }

    public static EncodedFragment newInstance(){
        return new EncodedFragment();
    }
}
