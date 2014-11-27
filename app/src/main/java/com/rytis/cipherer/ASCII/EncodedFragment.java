package com.rytis.cipherer.ASCII;

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
import android.widget.ImageButton;
import android.widget.Toast;

import com.rytis.cipherer.R;

/**
* Created by rytis on 14.10.29.
*/
public class EncodedFragment extends Fragment {
    EditText text;
    private EncodedInteractionListener mListener;

    public EncodedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ascii_cipher, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (EditText) view.findViewById(R.id.encodedText);

        setValues(getArguments().getString("initText", ""));

        ImageButton clearButton = (ImageButton) view.findViewById(R.id.clearText);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");
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
                try {
                    mListener.onChangedEncodedText(editable.toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.ascii_number_exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static EncodedFragment newInstance(String text){
        EncodedFragment fragment = new EncodedFragment();
        Bundle args = new Bundle();
        args.putString("initText", text);
        fragment.setArguments(args);
        return fragment;
    }

    public void setValues(String text) {
        this.text.setText(text);
    }

    public String getText() {
        return text.getText().toString();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EncodedInteractionListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement EncoderInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface EncodedInteractionListener {
        public void onChangedEncodedText(String text);
    }
}
