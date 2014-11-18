package com.rytis.cipherer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


public class CipheringActivity extends ActionBarActivity {

    private ListView menuList;
    private ArrayList<MyMenuItem> menuListItems = new ArrayList<>();

    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout drawerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciphering);
        int fragmentID = getIntent().getExtras().getInt("FragmentID");


        fragmentManager = getSupportFragmentManager();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerContainer = (LinearLayout) findViewById(R.id.drawer_container);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuList = (ListView) findViewById(R.id.drawer_list);

        final MenuAdapter adapter = new MenuAdapter(this, FragmentFactory.getListOfCiphers());
        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                final MyMenuItem item = (MyMenuItem) parent.getItemAtPosition(position);
                switchFragment(item, position);
            }

        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close);

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        fragmentManager.beginTransaction()
                .replace(R.id.container, FragmentFactory.getFragment(fragmentID))
                .commit();
        getSupportActionBar().setTitle(FragmentFactory.getLabelByID(fragmentID));
    }

    public void switchFragment(MyMenuItem listObject, int position) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, FragmentFactory.getFragment(listObject.getId()))
                .commit();
        menuList.setItemChecked(position, true);
        getSupportActionBar().setTitle(listObject.getLabel());
        drawerLayout.closeDrawer(drawerContainer);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
