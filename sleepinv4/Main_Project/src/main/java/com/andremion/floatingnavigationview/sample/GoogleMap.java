package com.andremion.floatingnavigationview.sample;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class GoogleMap extends FragmentActivity implements View.OnTouchListener , LocationListener{
    Button go_to_login,search_location;
    EditText search_location_field;
    String location="";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map);
        // setUpMapIfNeeded();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        getSharedPreferences("MyPref", 0).edit().clear().commit();
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

            search_location = (Button) findViewById(R.id.btn_search_location_user);

            search_location_field = (EditText) findViewById(R.id.et_search_location_user);

            go_to_login = (Button) findViewById(R.id.btn_go_to_main);
            go_to_login.setOnTouchListener(this);
            search_location.setOnTouchListener(this);



    }
//    public void onSearch(View view){
//
//             location= search_location_field.getText().toString();
//                List<Address> addresslist =null;
//
//        if (location!=null|| location.equals("")){
//                Geocoder geocoder = new Geocoder(this);
//                try{
//                    addresslist = geocoder.getFromLocationName(location ,1 );
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//                Address address =addresslist.get(0);
//                LatLng latlng = new LatLng(address.getLatitude(),address.getLongitude());
//
//
//
//
//            }
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(GoogleMap.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_UP:
                if (view.getId()== R.id.btn_go_to_main){
                    startActivity(new Intent(GoogleMap.this ,Login.class));
                    finish();
                }else if (view.getId()== R.id.btn_search_location_user){
                    location= search_location_field.getText().toString();
                    CustomMessageEvent event = new CustomMessageEvent();
                    event.setCustomMessage(location.toString());
                    EventBus.getDefault().post(event);
                }
        }

        return true;
    }


    @Override
    public void onLocationChanged(Location location) {

    }
}