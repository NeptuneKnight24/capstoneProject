package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private FloatingNavigationView mFloatingNavigationView;
    Spinner occupation_spinner;
    Button personal_profile,accommodations,board_and_lodging,google_map;
    TextView personal_profile_text,accommodations_text,board_and_lodging_text,google_map_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        personal_profile = (Button)findViewById(R.id.btn_personal_profile);
        accommodations = (Button)findViewById(R.id.btn_accommodations);
        board_and_lodging = (Button)findViewById(R.id.btn_board_and_lodging);
        google_map = (Button)findViewById(R.id.btn_google_map);

        personal_profile.setOnTouchListener(this);
        accommodations.setOnTouchListener(this);
        board_and_lodging.setOnTouchListener(this);
        google_map.setOnTouchListener(this);

        personal_profile_text = (TextView) findViewById(R.id.tv_pp);
        accommodations_text = (TextView) findViewById(R.id.tv_accom);
        board_and_lodging_text = (TextView) findViewById(R.id.tv_bandl);
        google_map_text = (TextView) findViewById(R.id.tv_ggmap);

        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = mFloatingNavigationView.getMenu();
                menu.findItem(R.id.nav_backhome).setVisible(false);
                mFloatingNavigationView.open();


            }
        });
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
                mFloatingNavigationView.close();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //test if a button has been touch
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                //test if what button has been touch
                if (view.getId() == R.id.btn_personal_profile) {
                    Toast.makeText(this, personal_profile_text.getText(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Personal_Profile.class));
                } else if (view.getId() == R.id.btn_accommodations) {
                    Toast.makeText(this, accommodations_text.getText(), Toast.LENGTH_SHORT).show();
                } else if (view.getId() == R.id.btn_board_and_lodging) {
                    Toast.makeText(this, board_and_lodging_text.getText(), Toast.LENGTH_SHORT).show();
                } else if (view.getId() == R.id.btn_google_map) {
                    Toast.makeText(this, google_map_text.getText(), Toast.LENGTH_SHORT).show();
                }
        }
        return true;
    }
}
