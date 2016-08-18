package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Registration extends Activity implements View.OnClickListener{
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                startActivity(new Intent(Registration.this,MainActivity.class));

        }
    }
}