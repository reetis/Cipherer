package com.rytis.cipherer;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MenuActivity extends ActionBarActivity {

    private ListView menuList;
    private ArrayList<MyMenuItem> menuListItems = new ArrayList<>();
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("CipherFragment", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_menu);

        menuList = (ListView) findViewById(R.id.menuList);

        final MenuAdapter adapter = new MenuAdapter(this, FragmentFactory.getListOfCiphers());
        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final MyMenuItem item = (MyMenuItem) parent.getItemAtPosition(position);
                switchWindow(item.getId());
            }

        });
    }

    public void switchWindow(int fragmentId) {
        Intent intent = new Intent(this, CipheringActivity.class);
        preferences.edit().putInt("FragmentID", fragmentId).apply();
        if(intent.resolveActivity(getPackageManager()) != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(intent);
            }
        } else {
            Toast toast = Toast.makeText(this, "Intent failed.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
