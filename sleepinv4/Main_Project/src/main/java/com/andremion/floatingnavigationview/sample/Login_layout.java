package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login_layout extends Activity implements View.OnTouchListener{
    Button btn_login;
    EditText user_field,pass_field;
    TextView register_now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        btn_login = (Button)findViewById(R.id.login_btn);
        user_field = (EditText)findViewById(R.id.et_username);
        pass_field = (EditText)findViewById(R.id.et_password);

        register_now = (TextView)findViewById(R.id.tv_text);
        register_now.setPaintFlags(register_now.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_login.setOnTouchListener(this);
        register_now.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //test if a button has been touch
       switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                //test if what button has been touch
                if (view.getId() == R.id.login_btn) {
                    startActivity(new Intent(Login_layout.this,MainActivity.class));
                }else if(view.getId() == R.id.tv_text) {
                    startActivity(new Intent(Login_layout.this,Registration.class));
                }
        }
        return true;
    }
}