package com.andremion.floatingnavigationview.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class GoogleMapLandowners extends FragmentActivity implements View.OnTouchListener{
Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_landowners);
        go_back=(Button)findViewById(R.id.btn_go_to_reg);
        go_back.setOnTouchListener(this);



    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_UP:
                startActivity(new Intent(GoogleMapLandowners.this,RegistrationLandowner.class));
                finish();
        }
        return true;
    }
}