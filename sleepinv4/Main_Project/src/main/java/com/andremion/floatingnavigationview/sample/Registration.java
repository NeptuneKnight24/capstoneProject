package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Registration extends Activity implements  AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    Spinner occupation_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);
        occupation_spinner= (Spinner)findViewById(R.id.spinner_status);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.occupation_status, android.R.layout.simple_spinner_dropdown_item);
        occupation_spinner.setAdapter(adapter);
        occupation_spinner.setOnItemSelectedListener(this);
        occupation_spinner.setBackgroundResource(R.drawable.button_spinner_selected);


    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}