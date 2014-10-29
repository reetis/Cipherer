package com.rytis.cipherer;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        intent.putExtra("FragmentID", fragmentId);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "Intent failed.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
