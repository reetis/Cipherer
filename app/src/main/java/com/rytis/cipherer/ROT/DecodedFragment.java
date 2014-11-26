package com.rytis.cipherer.ROT;

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
import android.widget.NumberPicker;

import com.rytis.cipherer.R;

public class DecodedFragment extends Fragment {
    EditText text;
    NumberPicker numberPicker;

    private DecodedInteractionListener mListener;

    public DecodedFragment() {
    }

    public static DecodedFragment newInstance(String text, int key){
        DecodedFragment fragment = new DecodedFragment();
        Bundle args = new Bundle();
        args.putString("initText", text);
        args.putInt("initKey", key);
        fragment.setArguments(args);
        return fragment;
    }

    public void setValues(String text, int key) {
        this.text.setText(text);
        setAutoMax(numberPicker, key);
        this.numberPicker.setValue(key);
    }

    public String getText() {
        return text.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rot_text, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (EditText) view.findViewById(R.id.decodedText);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setWrapSelectorWheel(false);

        setValues(getArguments().getString("initText", ""), getArguments().getInt("initKey", 0));

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                setAutoMax(numberPicker, newVal);
                mListener.onChangedKey(newVal);
            }
        });

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
    }

    private void setAutoMax(NumberPicker numberPicker, int newVal) {
        if (newVal >= numberPicker.getMaxValue()-10) {
            numberPicker.setMaxValue(newVal+20);
            numberPicker.setWrapSelectorWheel(false);
        }
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
        public void onChangedKey(int key);
        public void onChangedDecodedText(String text);
    }


}
