package com.rytis.cipherer;

import android.support.v4.app.Fragment;

import com.rytis.cipherer.ASCII.ASCIIFragment;
import com.rytis.cipherer.Braille.BrailleFragment;
import com.rytis.cipherer.ROT.ROTFragment;
import com.rytis.cipherer.Vignere.VignereFragment;

import java.util.LinkedList;

/**
 * Created by rytis on 14.10.28.
 */
public final class FragmentFactory {

    private static LinkedList<MyMenuItem> ciphersList;

    private FragmentFactory() {}
    public static Fragment getFragment(int fragmentID) {
        switch (fragmentID) {
            case 1:
                return VignereFragment.newInstance();
            case 2:
                return ROTFragment.newInstance();
            case 3:
                return ASCIIFragment.newInstance();
            case 4:
                return BrailleFragment.newInstance();
            default:
                return null;
        }
    }

    public static String getLabelByID(int fragmentID) {
        for(MyMenuItem i: ciphersList) {
            if (i.getId() == fragmentID) {
                return i.getLabel();
            }
        }
        return null;
    }

    public static LinkedList<MyMenuItem> getListOfCiphers(){
        if (ciphersList == null) {
            ciphersList = new LinkedList<>();
            ciphersList.add(new MyMenuItem("Vignere", 1));
            ciphersList.add(new MyMenuItem("ROT", 2));
            ciphersList.add(new MyMenuItem("ASCII", 3));
            ciphersList.add(new MyMenuItem("Braille", 4));
        }
        return ciphersList;
    }
}
