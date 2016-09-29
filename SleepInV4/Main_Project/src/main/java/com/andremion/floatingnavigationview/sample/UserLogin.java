package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLogin extends Activity implements View.OnTouchListener{
    Button btn_continue;
    private RadioGroup radioUsertypeGroup;
    private RadioButton radioButton_tenant,radioButton_landowner;
    TextView back_2_login_tv;


    String choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_choice_layout);

        //radiobutton/group reference
        radioButton_tenant =(RadioButton)findViewById(R.id.radioTenant);
        radioButton_landowner =(RadioButton)findViewById(R.id.radioLandowner);
        radioUsertypeGroup=(RadioGroup)findViewById(R.id.radioRegType);

        //textview reference
        back_2_login_tv=(TextView)findViewById(R.id.tv_text_goback);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Intro.otf");
        back_2_login_tv.setTypeface(face);
        back_2_login_tv.setPaintFlags(back_2_login_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        back_2_login_tv.setOnTouchListener(this);
        //button reference
        btn_continue = (Button)findViewById(R.id.continue_user_btn);
        //setting listeners
        btn_continue.setOnTouchListener(this);

        //set pre-defined value for radiobuttons
        choice="Tenant";

        radioUsertypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
              if (radioButton_tenant.isChecked()){
                  choice="Tenant";
              }else{
                  choice="Landowner";
              }
            }
        });


    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //test if a button has been touch
       switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                //test if what button has been touch
                if (view.getId() == R.id.continue_user_btn) {
                    if (choice.equals("Tenant")){
                        startActivity(new Intent(UserLogin.this,Registration.class));
                        finish();
                    }else{
                        startActivity(new Intent(UserLogin.this,RegistrationLandowner.class));
                        finish();
                    }
                }else if(view.getId() == R.id.tv_text_goback) {
                    startActivity(new Intent(UserLogin.this,Login.class));
                    finish();
                }

        }
        return true;
    }
}