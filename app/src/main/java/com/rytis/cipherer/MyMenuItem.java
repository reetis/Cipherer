package com.rytis.cipherer;

/**
* Created by rytis on 14.10.28.
*/
public class MyMenuItem {
    private String label;
    private int id;

    public MyMenuItem(String label, int id) {
        this.label = label;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return label;
    }
}
