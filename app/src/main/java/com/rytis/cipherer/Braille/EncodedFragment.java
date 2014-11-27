package com.rytis.cipherer.Braille;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.rytis.cipherer.R;

/**
* Created by rytis on 14.10.29.
*/
public class EncodedFragment extends Fragment {
    private EditText text;
    private ToggleButton dot1, dot2, dot3, dot4, dot5, dot6;
    private ImageButton enter, delete, space;
    private EncodedInteractionListener mListener;

    public EncodedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_braille_cipher, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (EditText) view.findViewById(R.id.encodedText);
        dot1 = (ToggleButton) view.findViewById(R.id.dot1);
        dot2 = (ToggleButton) view.findViewById(R.id.dot2);
        dot3 = (ToggleButton) view.findViewById(R.id.dot3);
        dot4 = (ToggleButton) view.findViewById(R.id.dot4);
        dot5 = (ToggleButton) view.findViewById(R.id.dot5);
        dot6 = (ToggleButton) view.findViewById(R.id.dot6);
        enter = (ImageButton) view.findViewById(R.id.enter);
        delete = (ImageButton) view.findViewById(R.id.delete);
        space = (ImageButton) view.findViewById(R.id.space);

        setValues(getArguments().getString("initText", ""));

        ImageButton clearButton = (ImageButton) view.findViewById(R.id.clearText);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");
                mListener.onChangedEncodedText(text.getText().toString());
            }
        });

        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.getEditableText().append(" ");
                mListener.onChangedEncodedText(text.getText().toString());
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable currentText = text.getText();
                if (currentText.length() > 0) {
                    currentText.delete(currentText.length() - 1,
                            currentText.length());
                    text.setText(currentText);
                }
                mListener.onChangedEncodedText(text.getText().toString());
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int offset = 0;
                if (dot1.isChecked()) { offset += 0b000001; }
                if (dot2.isChecked()) { offset += 0b000010; }
                if (dot3.isChecked()) { offset += 0b000100; }
                if (dot4.isChecked()) { offset += 0b001000; }
                if (dot5.isChecked()) { offset += 0b010000; }
                if (dot6.isChecked()) { offset += 0b100000; }
                int codePoint = 10240 + offset;
                char character = (char) codePoint;
                text.getEditableText().append(character);
                dot1.setChecked(false);
                dot2.setChecked(false);
                dot3.setChecked(false);
                dot4.setChecked(false);
                dot5.setChecked(false);
                dot6.setChecked(false);
                mListener.onChangedEncodedText(text.getText().toString());
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
