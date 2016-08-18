package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Login_layout extends Activity implements View.OnClickListener{
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

        register_now = (TextView)findViewById(R.id.tv_register);
        register_now.setPaintFlags(register_now.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_login.setOnClickListener(this);
        register_now.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                startActivity(new Intent(Login_layout.this,MainActivity.class));
            case R.id.tv_register:
                startActivity(new Intent(Login_layout.this,Registration.class));
        }
    }
}