package com.rytis.cipherer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MenuActivity extends Activity {

    private ListView menuList;
    private ArrayList<MyMenuItem> menuListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuListItems.add(new MyMenuItem(getString(R.string.vignere), Vignere.class));

        menuList = (ListView) findViewById(R.id.menuList);

        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, menuListItems);
        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final MyMenuItem item = (MyMenuItem) parent.getItemAtPosition(position);
                switchWindow(item.getTargetActivity());
            }

        });
    }

    public void switchWindow(Class newActivity) {
        Intent intent = new Intent(this, newActivity);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "Intent failed.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private class StableArrayAdapter extends ArrayAdapter<MyMenuItem> {
        HashMap<MyMenuItem, Integer> mIdMap = new HashMap<>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<MyMenuItem> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i){
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            MyMenuItem item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    private static class MyMenuItem {
        private String label;
        private Class targetActivity;

        public MyMenuItem(String label, Class targetActivity) {
            this.label = label;
            this.targetActivity = targetActivity;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Class getTargetActivity() {
            return targetActivity;
        }

        public void setTargetActivity(Class targetActivity) {
            this.targetActivity = targetActivity;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
