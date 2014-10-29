package com.rytis.cipherer.Vignere;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rytis.cipherer.R;

public class DecodedFragment extends Fragment {
    EditText text;
    EditText key;

    private DecodedInteractionListener mListener;

    public DecodedFragment() {
        // Required empty public constructor
    }

    public static DecodedFragment newInstance(){
        return new DecodedFragment();
    }

    public void setValues(String text, String key) {
        this.text.setText(text);
        this.key.setText(key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vignere_text, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (EditText) view.findViewById(R.id.decodedText);
        key = (EditText) view.findViewById(R.id.keyText);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mListener.onChangedDecodedText(editable.toString());
            }
        });

        key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mListener.onChangedKey(editable.toString());
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DecodedInteractionListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement DecoderInteractionListener");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DecodedInteractionListener {
        public void onChangedKey(String key);
        public void onChangedDecodedText(String text);
    }


}
