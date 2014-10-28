package com.rytis.cipherer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
* Created by rytis on 14.10.28.
*/
public class MenuAdapter extends BaseAdapter {
    private LinkedList<MyMenuItem> itemList;
    private LayoutInflater layoutInflater;
    private Context context;

    public MenuAdapter(Context context, LinkedList<MyMenuItem> objects) {
        itemList = objects;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        }
        TextView label = (TextView) view.findViewById(R.id.label);
        label.setText(((MyMenuItem)this.getItem(i)).getLabel());
        return view;
    }
}
