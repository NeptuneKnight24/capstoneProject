package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Random;

public class RegistrationLandowner extends Activity implements  AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener,View.OnTouchListener {
    private RadioGroup radioSexGroup;
    private RadioButton radioButton_male,radioButton_female;
    TextView back_2_login_tv;
    Button register_btn;
    EditText lastname_et,firstname_et,contact_number_et,email_address_et,username_et,password_et,confirm_password_et;
    Spinner occupation_spinner;
    //variables for textfield values
    String lastname_val="",firstname_val="",contact_number_val="",email_address_val="",
            username_val="",password_val="", final_password="",occupation_stat_val="",sex_type_val="",random_id="";
    AlertDialog.Builder builder;
    String reg_url = "http://192.168.22.3/register.php";
    int i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_landowner_layout);



        //textfields reference
        lastname_et=(EditText)findViewById(R.id.et_lastname);
        firstname_et=(EditText)findViewById(R.id.et_firstname);
        contact_number_et=(EditText)findViewById(R.id.et_contact_number);
        email_address_et=(EditText)findViewById(R.id.et_email_address);
        username_et=(EditText)findViewById(R.id.et_username);
        password_et=(EditText)findViewById(R.id.et_password);
        builder = new AlertDialog.Builder(RegistrationLandowner.this);
        confirm_password_et=(EditText)findViewById(R.id.et_confirm_password);

        //radiobuttons reference
        radioButton_male =(RadioButton)findViewById(R.id.radioMale);
        radioButton_female =(RadioButton)findViewById(R.id.radioFemale);


        //button reference
        register_btn= (Button)findViewById(R.id.register_user_btn);
        //set the onTouchListener to perform actions
        register_btn.setOnTouchListener(this);


        //radiobuttons reference
        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);
        occupation_spinner= (Spinner)findViewById(R.id.spinner_status);

        //textview reference
        back_2_login_tv=(TextView)findViewById(R.id.tv_text_goback);
        //set the onTouchListener to perform actions
        back_2_login_tv.setOnTouchListener(this);


        //populate dropdown/spinner component
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.occupation_status, android.R.layout.simple_spinner_dropdown_item);
        occupation_spinner.setAdapter(adapter);
        occupation_spinner.setOnItemSelectedListener(this);
        occupation_spinner.setBackgroundResource(R.drawable.button_spinner_selected);

        //set the values for the variable for spinner and radiobuttons
        occupation_stat_val = "Employed";
        sex_type_val="MALE";


        //set values for radiobutton variable
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButton_male.isChecked()){
                    sex_type_val = "MALE";
                }else {
                    sex_type_val = "FEMALE";
                }
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        occupation_stat_val=occupation_spinner.getSelectedItem().toString();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        occupation_stat_val=occupation_spinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        //set the values for the variables for textfields
        lastname_val = lastname_et.getText().toString();
        firstname_val = firstname_et.getText().toString();
        contact_number_val = contact_number_et.getText().toString();
        email_address_val= email_address_et.getText().toString();
        username_val= username_et.getText().toString();
        password_val=password_et.getText().toString();
        final_password= confirm_password_et.getText().toString();
        sex_type_val.toString();
        Random r = new Random();
        i1=r.nextInt(50000-1);
        random_id=String.valueOf(i1);


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                //test if what button has been touch
                if (view.getId() == R.id.register_user_btn) {
                    if (lastname_val.trim().isEmpty() || firstname_val.trim().isEmpty() || contact_number_val.trim().isEmpty() ||
                            email_address_val.trim().isEmpty() ||username_val.trim().isEmpty()||password_val.trim().isEmpty()||
                            final_password.trim().isEmpty())
                    {
                        builder.setTitle("Something went wrong.....");
                        builder.setMessage("Please fill up all the fields...");
                        displayAlert("input_error");
                    }
                    else
                    {
                        if (!password_val.equals(final_password))
                        {
                            builder.setTitle("Something went wrong.....");
                            builder.setMessage("Your Passwords are not matching....");
                            displayAlert("input_error");
                        }
                        else
                        {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONArray jsonArray = new JSONArray(response);
                                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                                String code = jsonObject.getString("code");
                                                String message = jsonObject.getString("message");
                                                builder.setTitle("Server Response....");
                                                builder.setMessage(message);
                                                displayAlert(code);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<String, String>();
                                    params.put("id",random_id);
                                    params.put("lname",lastname_val);
                                    params.put("fname",firstname_val);
                                    params.put("gender",sex_type_val);
                                    params.put("level",occupation_stat_val);
                                    params.put("num",contact_number_val);
                                    params.put("uname",username_val);
                                    params.put("email",email_address_val);
                                    params.put("pword",final_password);

                                    return params;
                                }
                            };
                            MySingleton.getInstance(RegistrationLandowner.this).addToRequestque(stringRequest);
                          /* Toast.makeText(this, ""+lastname_val+"\n"+firstname_val+"\n"+sex_type_val+"\n"+occupation_stat_val+"\n"+contact_number_val+
                                       ""+email_address_val+"\n"+username_val+"\n"+password_val+"\n"+final_password
                                      , Toast.LENGTH_SHORT).show();*/
                        }
                    }

                }else if (view.getId() == R.id.tv_text_goback)
                {
                    //perform action - go to layout
                    startActivity(new Intent(RegistrationLandowner.this,Login.class));
                    finish();

                }

        }
        return true;
    }

    private void displayAlert(final String code) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("input_error")) {
                    password_et.setText("");
                    confirm_password_et.setText("");
                }
                else if (code.equals("reg_success"))
                {
                    finish();
                }
                else if (code.equals("reg_failed"))
                {
                    lastname_et.setText("");
                    firstname_et.setText("");
                    contact_number_et.setText("");
                    email_address_et.setText("");
                    username_et.setText("");
                    password_et.setText("");
                    confirm_password_et.setText("");
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}