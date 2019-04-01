package com.rotamobile.gursan.ui.bottom_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;

public class MainBottomNavigation extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton back;
    private BottomNavigationView btm_navigationView;
    private TextView txt_toolbar;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    public static int navItemIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_home);

        txt_toolbar = findViewById(R.id.toolbar_title);
        txt_toolbar.setText("Tüm Mesajlar");

        toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.bottom_nav_item);

        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go_main = new Intent(getApplicationContext(),Main.class);
                go_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                go_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(go_main);
            }
        });


        btm_navigationView = findViewById(R.id.navigation);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


         //btm_navigationView.setItemIconTintList(null);
         loadFragment(new AllMessages());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_tum_mesajlar:
                    txt_toolbar.setText("Tüm Mesajlar");
                    navItemIndex = 0;
                    fragment = new AllMessages();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_acil_mesajlar:
                    txt_toolbar.setText("Açık Mesajlar");
                    navItemIndex = 1;
                    fragment = new EmergencyMessages();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_bayrakli_mesajlar:
                    txt_toolbar.setText("Kapalı Mesajlar");
                    navItemIndex = 2;
                    fragment = new FlagMessages();
                    loadFragment(fragment);
                    return true;

            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {

        setToolbarTitle();
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    private void setToolbarTitle() {

        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent go_main = new Intent(getApplicationContext(),Main.class);
        go_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        go_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(go_main);
    }


}
