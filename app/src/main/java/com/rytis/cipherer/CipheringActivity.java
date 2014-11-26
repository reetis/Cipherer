package com.rytis.cipherer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ciphering);
        preferences = getSharedPreferences("CipherFragment", Context.MODE_PRIVATE);

        int fragmentID = preferences.getInt("FragmentID", 1);


        fragmentManager = getSupportFragmentManager();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerContainer = (LinearLayout) findViewById(R.id.drawer_container);

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
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                supportInvalidateOptionsMenu();
            }
        };

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
        preferences.edit().putInt("FragmentID", listObject.getId()).apply();
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
    protected void onResume() {
        super.onResume();


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
