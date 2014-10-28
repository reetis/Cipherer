package com.rytis.cipherer;

import android.support.v4.app.Fragment;

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
            //ciphersList.add(new MyMenuItem("ROT", 2));
        }
        return ciphersList;
    }
}
