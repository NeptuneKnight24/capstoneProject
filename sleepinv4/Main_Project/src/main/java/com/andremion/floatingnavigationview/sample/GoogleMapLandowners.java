package com.andremion.floatingnavigationview.sample;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GoogleMapLandowners extends FragmentActivity implements View.OnTouchListener{
    Button go_back,set_location,search_location;
    EditText search_location_field;
    String location="";
    String latitude="",longitude="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_landowners);

        EventBus.getDefault().register(this);
        set_location =(Button)findViewById(R.id.btn_set_location);
        search_location =(Button)findViewById(R.id.btn_search_location);

        search_location_field =(EditText)findViewById (R.id.et_search_location);
        go_back=(Button)findViewById(R.id.btn_go_to_reg);
        go_back.setOnTouchListener(this);
        set_location.setOnTouchListener(this);
        search_location.setOnTouchListener(this);
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                if (view.getId() == R.id.btn_set_location) {
                    if (latitude.trim().isEmpty() && longitude.trim().isEmpty()){
                        Toast.makeText(this,"No location selected, Please tap a location on the map", Toast.LENGTH_SHORT).show();
                        }else {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("location", location);
                            editor.putString("latitude", latitude);
                            editor.putString("longitude", longitude);
                            editor.commit();
                            startActivity(new Intent(GoogleMapLandowners.this, RegistrationLandowner.class));
                            finish();
                        }
                    }else if (view.getId() == R.id.btn_go_to_reg){
                        startActivity(new Intent(GoogleMapLandowners.this, RegistrationLandowner.class));
                        finish();
                    }else if (view.getId() == R.id.btn_search_location){
                        location= search_location_field.getText().toString();
                        CustomMessageEventSearchLandowner event_search = new CustomMessageEventSearchLandowner();
                        event_search.setCustomMessage(location.toString());
                        EventBus.getDefault().post(event_search);
                }
        }
        return true;
    }
    @Subscribe
    public void onEvent(CustomMessageEvent event){
        search_location_field.setText(event.getCustomMessage());
        location= search_location_field.getText().toString();

    }
    @Subscribe
    public void onEvent(CustomMessageEventLatitude event_latitude){
        latitude = event_latitude.getCustomMessage();

    }
    @Subscribe
    public void onEvent(CustomMessageEventLongitude event_longitude){
        longitude = event_longitude.getCustomMessage();

    }



}