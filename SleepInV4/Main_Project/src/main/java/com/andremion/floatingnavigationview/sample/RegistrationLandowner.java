package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegistrationLandowner extends Activity implements  AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener,View.OnTouchListener {
    private RadioGroup radioSexGroup;
    private RadioButton radioButton_male,radioButton_female;
    private ImageView for_profilepic;
    LinearLayout apartment_group,transient_group,bedspace_group;
    LinearLayout apartment_location,transient_location,bedspace_location;
    CheckBox apartment,bedspace,transients;
    TextView back_2_login_tv,landowner_label,perinfo_label,landinfo_label,userinfo_label;
    Button register_btn,back_to_choice_btn,select_image;
    Button search_location_apartment,search_location_transient,search_location_bedspace;
    EditText lastname_et,firstname_et,middlename_et,contact_number_et,email_address_et, username_et,password_et,confirm_password_et;
    EditText apartment_name_et,transient_name_et,bedspace_name_et,apartment_business_permit_et,transient_business_permit_et,bedspace_business_permit_et,
            apartment_number_of_units_et,transient_number_of_units_et,bedspace_number_of_units_et,apartment_fee_per_unit_et,transient_fee_per_unit_et,bedspace_fee_per_unit_et,
            apartment_location_et,transient_location_et,bedspace_location_et;

    String control="";

    //variables for textfield values
    String lastname_val="",firstname_val="",middlename_val="",contact_number_val="",email_address_val="",
            username_val="",password_val="", final_password="",sex_type_val="",random_id="";

    //variables for building_name and building permit
    String apartment_name_val,transient_name_val,bedspace_name_val,apartment_business_permit_val,transient_business_permit_val,
            bedspace_business_permit_val;

    //variables for number of units and fee per unit
    String apartment_number_of_units_val,transient_number_of_units_val,bedspace_number_of_units_val,apartment_fee_per_unit_val,
            transient_fee_per_unit_val,bedspace_fee_per_unit_val;

    //varaibles for building location
    String apartment_location_val,transient_location_val,bedspace_location_val;
    private String KEY_IMAGE = "image";

    AlertDialog.Builder builder;
    String reg_url = "http://sleepin.comli.com/register-landowner.php";
    int i1;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_landowner_layout);
        builder = new AlertDialog.Builder(RegistrationLandowner.this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);



        //view references
        apartment_group = (LinearLayout)findViewById(R.id.ll_apartment_group);
        transient_group = (LinearLayout)findViewById(R.id.ll_transient_group);
        bedspace_group = (LinearLayout)findViewById(R.id.ll_bedspace_group);

        apartment_location = (LinearLayout)findViewById(R.id.ll_apartment_location_group);
        transient_location = (LinearLayout)findViewById(R.id.ll_transient_location_group);
        bedspace_location = (LinearLayout)findViewById(R.id.ll_bedspace_location_group);

        //set visibility to gone on activity start
        apartment_group.setVisibility(View.GONE);
        transient_group.setVisibility(View.GONE);
        bedspace_group.setVisibility(View.GONE);

        apartment_location.setVisibility(View.GONE);
        transient_location.setVisibility(View.GONE);
        bedspace_location.setVisibility(View.GONE);



        //checkbox reference with actions
        apartment =(CheckBox)findViewById(R.id.cb_apartment);
        apartment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    apartment_group.setVisibility(View.VISIBLE);
                    apartment_location.setVisibility(View.VISIBLE);
                }else{
                    apartment_group.setVisibility(View.GONE);
                    apartment_location.setVisibility(View.GONE);
                }
            }
        });
        bedspace =(CheckBox)findViewById(R.id.cb_bedspace);
        bedspace.setVisibility(View.GONE);
        bedspace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    bedspace_group.setVisibility(View.VISIBLE);
                    bedspace_location.setVisibility(View.VISIBLE);
                }else{
                    bedspace_group.setVisibility(View.GONE);
                    bedspace_location.setVisibility(View.GONE);
                }
            }
        });
        transients =(CheckBox)findViewById(R.id.cb_transients);
        transients.setVisibility(View.GONE);
        transients.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    transient_group.setVisibility(View.VISIBLE);
                    transient_location.setVisibility(View.VISIBLE);
                }else{
                   transient_group.setVisibility(View.GONE);
                   transient_location.setVisibility(View.GONE);
                }
            }
        });

        //label reference
        perinfo_label =(TextView)findViewById(R.id.tv_perinfo_label);
        landowner_label =(TextView)findViewById(R.id.tv_tenant_label);
        landinfo_label =(TextView)findViewById(R.id.tv_landinfo_label);
        userinfo_label =(TextView)findViewById(R.id.tv_userinfo_label);

        //for custom fonts
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Intro.otf");
        landowner_label.setTypeface(face);
        perinfo_label.setTypeface(face);
        landinfo_label.setTypeface(face);
        userinfo_label.setTypeface(face);

        //imageview reference
        for_profilepic =(ImageView)findViewById(R.id.iv_profile_picture);

        //edit text fields reference
        lastname_et=(EditText)findViewById(R.id.et_lastname);
        firstname_et=(EditText)findViewById(R.id.et_firstname);
        middlename_et=(EditText)findViewById(R.id.et_middlename);
        contact_number_et=(EditText)findViewById(R.id.et_contact_number);
        email_address_et=(EditText)findViewById(R.id.et_email_address);
        username_et=(EditText)findViewById(R.id.et_username);
        password_et=(EditText)findViewById(R.id.et_password);
        confirm_password_et=(EditText)findViewById(R.id.et_confirm_password);

        apartment_name_et =(EditText)findViewById(R.id.et_apartment_name);
        transient_name_et =(EditText)findViewById(R.id.et_transient_name);
        bedspace_name_et =(EditText)findViewById(R.id.et_bedspace_name);

        apartment_business_permit_et =(EditText)findViewById(R.id.et_apartment_bp);
        transient_business_permit_et =(EditText)findViewById(R.id.et_transient_bp);
        bedspace_business_permit_et =(EditText)findViewById(R.id.et_bedspace_bp);

        apartment_number_of_units_et =(EditText)findViewById(R.id.et_apartment_no_units);
        transient_number_of_units_et =(EditText)findViewById(R.id.et_transient_no_units);
        bedspace_number_of_units_et =(EditText)findViewById(R.id.et_bedspace_no_units);

        apartment_fee_per_unit_et = (EditText)findViewById(R.id.et_apartment_price);
        transient_fee_per_unit_et= (EditText)findViewById(R.id.et_bedspace_price);
        bedspace_fee_per_unit_et = (EditText)findViewById(R.id.et_transient_price);

        apartment_location_et =(EditText)findViewById(R.id.et_apartment_location);
        apartment_location_et.setKeyListener(null);
        transient_location_et =(EditText)findViewById(R.id.et_transient_location);
        bedspace_location_et =(EditText)findViewById(R.id.et_bedspace_location);



        //radiobuttons reference
        radioButton_male =(RadioButton)findViewById(R.id.radioMale);
        radioButton_female =(RadioButton)findViewById(R.id.radioFemale);

        //button reference
        select_image = (Button)findViewById(R.id.btn_insert_image);
        back_to_choice_btn= (Button)findViewById(R.id.btn_back_to_choice);
        register_btn= (Button)findViewById(R.id.register_user_btn);

        search_location_apartment =(Button)findViewById(R.id.btn_search_location_apartment);
        search_location_transient =(Button)findViewById(R.id.btn_search_location_transient);
        search_location_bedspace =(Button)findViewById(R.id.btn_search_location_bedspace);

        //set the onTouchListener to perform actions
        search_location_apartment.setOnTouchListener(this);
        search_location_transient.setOnTouchListener(this);
        search_location_bedspace.setOnTouchListener(this);
        register_btn.setOnTouchListener(this);
        back_to_choice_btn.setOnTouchListener(this);
        select_image.setOnTouchListener(this);

        //radiobuttons reference
        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);

        //textview reference
        back_2_login_tv=(TextView)findViewById(R.id.tv_text_goback);
        back_2_login_tv.setTypeface(face);

        //set the onTouchListener to perform actions
        back_2_login_tv.setOnTouchListener(this);

        //initial value for radiao buttons
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

        //shared preferences value
        firstname_et.setText(pref.getString("firstname",null));
        lastname_et.setText(pref.getString("lastname",null));
        middlename_et.setText(pref.getString("middlename",null));
        contact_number_et.setText(pref.getString("contact",null));
        email_address_et.setText(pref.getString("email",null));
        apartment_business_permit_et.setText(pref.getString("buildingpermit",null));
        apartment_name_et.setText(pref.getString("buildingname",null));
        apartment_number_of_units_et.setText(pref.getString("buildingunits",null));
        apartment_fee_per_unit_et.setText(pref.getString("unitprice",null));
        username_et.setText(pref.getString("username",null));
        password_et.setText(pref.getString("password",null));
        confirm_password_et.setText(pref.getString("password",null));
        apartment_location_et.setText(pref.getString("location",null));









    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                for_profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        //set the values for the variables for textfields
        lastname_val = lastname_et.getText().toString();
        firstname_val = firstname_et.getText().toString();
        middlename_val = middlename_et.getText().toString();
        contact_number_val = contact_number_et.getText().toString();
        email_address_val= email_address_et.getText().toString();


        if (apartment.isChecked()==true && transients.isChecked()==false && bedspace.isChecked()==false)
        {
            apartment_name_val = apartment_name_et.getText().toString();
            apartment_business_permit_val = apartment_business_permit_et.getText().toString();
            apartment_number_of_units_val = apartment_number_of_units_et.getText().toString();
            apartment_fee_per_unit_val = apartment_fee_per_unit_et.getText().toString();
            apartment_location_val = apartment_location_et.getText().toString();
            control="1a";
        }
        else if (apartment.isChecked()==true && transients.isChecked()==true && bedspace.isChecked()==false)
        {
            apartment_name_val = apartment_name_et.getText().toString();
            apartment_business_permit_val = apartment_business_permit_et.getText().toString();
            apartment_number_of_units_val = apartment_number_of_units_et.getText().toString();
            apartment_fee_per_unit_val = apartment_fee_per_unit_et.getText().toString();
            apartment_location_val = apartment_location_et.getText().toString();

            transient_name_val = transient_name_et.getText().toString();
            transient_business_permit_val = transient_business_permit_et.getText().toString();
            transient_number_of_units_val = transient_number_of_units_et.getText().toString();
            transient_fee_per_unit_val = transient_fee_per_unit_et.getText().toString();
            transient_location_val = transient_location_et.getText().toString();
            control="1ab";
        }
        else if (apartment.isChecked()==true && transients.isChecked()==true && bedspace.isChecked()==true)
        {
            apartment_name_val = apartment_name_et.getText().toString();
            apartment_business_permit_val = apartment_business_permit_et.getText().toString();
            apartment_number_of_units_val = apartment_number_of_units_et.getText().toString();
            apartment_fee_per_unit_val = apartment_fee_per_unit_et.getText().toString();
            apartment_location_val = apartment_location_et.getText().toString();

            transient_name_val = transient_name_et.getText().toString();
            transient_business_permit_val = transient_business_permit_et.getText().toString();
            transient_number_of_units_val = transient_number_of_units_et.getText().toString();
            transient_fee_per_unit_val = transient_fee_per_unit_et.getText().toString();
            transient_location_val = transient_location_et.getText().toString();

            bedspace_name_val = bedspace_name_et.getText().toString();
            bedspace_business_permit_val = bedspace_business_permit_et.getText().toString();
            bedspace_number_of_units_val = bedspace_number_of_units_et.getText().toString();
            bedspace_fee_per_unit_val = bedspace_fee_per_unit_et.getText().toString();
            bedspace_location_val = bedspace_location_et.getText().toString();
            control="1abc";

        }
        else if (apartment.isChecked()==false && transients.isChecked()==true && bedspace.isChecked()==false)
        {
            transient_name_val = transient_name_et.getText().toString();
            transient_business_permit_val = transient_business_permit_et.getText().toString();
            transient_number_of_units_val = transient_number_of_units_et.getText().toString();
            transient_fee_per_unit_val = transient_fee_per_unit_et.getText().toString();
            transient_location_val = transient_location_et.getText().toString();
            control="2b";
        }
        else if (apartment.isChecked()==false && transients.isChecked()==true && bedspace.isChecked()==true)
        {
            transient_name_val = transient_name_et.getText().toString();
            transient_business_permit_val = transient_business_permit_et.getText().toString();
            transient_number_of_units_val = transient_number_of_units_et.getText().toString();
            transient_fee_per_unit_val = transient_fee_per_unit_et.getText().toString();
            transient_location_val = transient_location_et.getText().toString();

            bedspace_name_val = bedspace_name_et.getText().toString();
            bedspace_business_permit_val = bedspace_business_permit_et.getText().toString();
            bedspace_number_of_units_val = bedspace_number_of_units_et.getText().toString();
            bedspace_fee_per_unit_val = bedspace_fee_per_unit_et.getText().toString();
            bedspace_location_val = bedspace_location_et.getText().toString();
            control="2bc";
        }
        else if (apartment.isChecked()==false && transients.isChecked()==false && bedspace.isChecked()==true)
        {
            bedspace_name_val = bedspace_name_et.getText().toString();
            bedspace_business_permit_val = bedspace_business_permit_et.getText().toString();
            bedspace_number_of_units_val = bedspace_number_of_units_et.getText().toString();
            bedspace_fee_per_unit_val = bedspace_fee_per_unit_et.getText().toString();
            bedspace_location_val = bedspace_location_et.getText().toString();
            control="3c";
        }
        else if (apartment.isChecked()==true && transients.isChecked()==false && bedspace.isChecked()==true)
        {
            apartment_name_val = apartment_name_et.getText().toString();
            apartment_business_permit_val = apartment_business_permit_et.getText().toString();
            apartment_number_of_units_val = apartment_number_of_units_et.getText().toString();
            apartment_fee_per_unit_val = apartment_fee_per_unit_et.getText().toString();
            apartment_location_val = apartment_location_et.getText().toString();

            bedspace_name_val = bedspace_name_et.getText().toString();
            bedspace_business_permit_val = bedspace_business_permit_et.getText().toString();
            bedspace_number_of_units_val = bedspace_number_of_units_et.getText().toString();
            bedspace_fee_per_unit_val = bedspace_fee_per_unit_et.getText().toString();
            bedspace_location_val = bedspace_location_et.getText().toString();
            control="3ac";
        }

        username_val = username_et.getText().toString();
        password_val = password_et.getText().toString();
        final_password = confirm_password_et.getText().toString();


        sex_type_val.toString();
        Random r = new Random();
        i1=r.nextInt(50000-1);
        random_id=String.valueOf(i1);

        String character = "@";

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
                    else if (!email_address_val.contains(character))
                    {
                        builder.setTitle("Something went wrong.....");
                        builder.setMessage("Email address INVALID");
                        email_address_et.requestFocus();
                        displayAlert("input_error");
                    } else if (apartment.isChecked()==false && transients.isChecked()==false && bedspace.isChecked()==false)
                    {
                        builder.setTitle("Something went wrong.....");
                        builder.setMessage("Insufficient business information");
                        email_address_et.requestFocus();
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
                        {
                            final ProgressDialog loading = ProgressDialog.show(this,"Connecting...","Please wait...",false,false);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                loading.dismiss();
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
                                    loading.dismiss();
                                    Toast.makeText(RegistrationLandowner.this,"Connection Error",Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<String, String>();
                                    //Converting Bitmap to String
                                    String image = getStringImage(bitmap);
                                    params.put("uid",random_id);
                                    params.put("lname",lastname_val.toUpperCase());
                                    params.put("fname",firstname_val.toUpperCase());
                                    params.put("midname",middlename_val.toUpperCase());
                                    params.put("gender",sex_type_val.toUpperCase());
//                                    if(control.toString().contains("1a")) {
                                    params.put("apartment_name",apartment_name_val.toUpperCase());
                                    params.put("apartment_permit",apartment_business_permit_val);
                                    params.put("apartment_available_space",apartment_number_of_units_val);
                                    params.put("apartment_fee_per_unit",apartment_fee_per_unit_val);
                                    params.put("apartment_location",apartment_location_val.toUpperCase());

//                                    }else if (control.toString().contains("1ab")){
//                                        params.put("apartment_name",apartment_name_val);
//                                        params.put("apartment_permit",apartment_business_permit_val);
//                                        params.put("apartment_available_space",apartment_number_of_units_val);
//                                        params.put("apartment_fee_per_unit",apartment_fee_per_unit_val);
//                                        params.put("apartment_location",apartment_location_val);
//
//                                        params.put("transient_name",transient_name_val);
//                                        params.put("transient_permit",transient_business_permit_val);
//                                        params.put("transient_available_space",transient_number_of_units_val);
//                                        params.put("transient_fee_per_unit",transient_fee_per_unit_val);
//                                        params.put("transient_location",transient_location_val);
//
//                                    }else if (control.toString().contains("1abc")){
//                                        params.put("apartment_name",apartment_name_val);
//                                        params.put("apartment_permit",apartment_business_permit_val);
//                                        params.put("apartment_available_space",apartment_number_of_units_val);
//                                        params.put("apartment_fee_per_unit",apartment_fee_per_unit_val);
//                                        params.put("apartment_location",apartment_location_val);
//
//                                        params.put("transient_name",transient_name_val);
//                                        params.put("transient_permit",transient_business_permit_val);
//                                        params.put("transient_available_space",transient_number_of_units_val);
//                                        params.put("transient_fee_per_unit",transient_fee_per_unit_val);
//                                        params.put("transient_location",transient_location_val);
//
//                                        params.put("bedspace_name",bedspace_name_val);
//                                        params.put("bedspace_permit",bedspace_business_permit_val);
//                                        params.put("bedspace_available_space",bedspace_number_of_units_val);
//                                        params.put("bedspace_fee_per_unit",bedspace_fee_per_unit_val);
//                                        params.put("bedspace_location",bedspace_location_val);
//
//                                    }else if(control.toString().contains("2b")){
//
//                                        params.put("transient_name",transient_name_val);
//                                        params.put("transient_permit",transient_business_permit_val);
//                                        params.put("transient_available_space",transient_number_of_units_val);
//                                        params.put("transient_fee_per_unit",transient_fee_per_unit_val);
//                                        params.put("transient_location",transient_location_val);
//                                    }else if(control.toString().contains("2bc")){
//
//                                        params.put("transient_name",transient_name_val);
//                                        params.put("transient_permit",transient_business_permit_val);
//                                        params.put("transient_available_space",transient_number_of_units_val);
//                                        params.put("transient_fee_per_unit",transient_fee_per_unit_val);
//                                        params.put("transient_location",transient_location_val);
//
//                                        params.put("bedspace_name",bedspace_name_val);
//                                        params.put("bedspace_permit",bedspace_business_permit_val);
//                                        params.put("bedspace_available_space",bedspace_number_of_units_val);
//                                        params.put("bedspace_fee_per_unit",bedspace_fee_per_unit_val);
//                                        params.put("bedspace_location",bedspace_location_val);
//
//                                    }else if(control.toString().contains("3c")){
//                                        params.put("bedspace_name",bedspace_name_val);
//                                        params.put("bedspace_permit",bedspace_business_permit_val);
//                                        params.put("bedspace_available_space",bedspace_number_of_units_val);
//                                        params.put("bedspace_fee_per_unit",bedspace_fee_per_unit_val);
//                                        params.put("bedspace_location",bedspace_location_val);
//
//                                    }else if(control.toString().contains("3ac")){
//                                        params.put("bedspace_name",bedspace_name_val);
//                                        params.put("bedspace_permit",bedspace_business_permit_val);
//                                        params.put("bedspace_available_space",bedspace_number_of_units_val);
//                                        params.put("bedspace_fee_per_unit",bedspace_fee_per_unit_val);
//                                        params.put("bedspace_location",bedspace_location_val);
//
//                                        params.put("apartment_name",apartment_name_val);
//                                        params.put("apartment_permit",apartment_business_permit_val);
//                                        params.put("apartment_available_space",apartment_number_of_units_val);
//                                        params.put("apartment_fee_per_unit",apartment_fee_per_unit_val);
//                                        params.put("apartment_location",apartment_location_val);
//                                    }

                                    params.put("num",contact_number_val.toUpperCase());
                                    params.put("email",email_address_val.toUpperCase());
                                    params.put("uname",username_val);
                                    params.put("pword",final_password);
                                    params.put(KEY_IMAGE, image);
                                    return params;

                                }
                            };
                            MySingleton.getInstance(RegistrationLandowner.this).addToRequestque(stringRequest);
//                           Toast.makeText(this, ""+lastname_val+"\n"+firstname_val+"\n"+middlename_val+"\n"+sex_type_val+"\n"+contact_number_val+
//                                       ""+email_address_val+"\n"+username_val+"\n"+password_val+"\n"+final_password+"\n"+apartment_name_val
//                                           +"\n"+apartment_business_permit_val+"\n"+apartment_fee_per_unit_val+"\n"+apartment_number_of_units_val
//                                           +"\n"+apartment_location_val
//                                      , Toast.LENGTH_SHORT).show();
                        }
                    }
                }else if (view.getId() == R.id.tv_text_goback) {
                    //perform action - go to layout
                    startActivity(new Intent(RegistrationLandowner.this,Login.class));
                    finish();
                }else if (view.getId() == R.id.btn_insert_image) {
                    //perform action - select an image from the gallery
                    showFileChooser();
                }else if (view.getId() == R.id.btn_back_to_choice){
                    startActivity(new Intent(RegistrationLandowner.this,Login.class));
                    finish();
                }else if (view.getId() == R.id.btn_search_location_apartment){
                    apartment_location_et.setText("");
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("lastname",lastname_val);
                    editor.putString("firstname",firstname_val);
                    editor.putString("middlename",middlename_val);
                    editor.putString("contact",contact_number_val);
                    editor.putString("email",email_address_val);
                    editor.putString("buildingpermit",apartment_business_permit_val);
                    editor.putString("buildingname",apartment_name_val);
                    editor.putString("buildingunits",apartment_number_of_units_val);
                    editor.putString("unitprice",apartment_fee_per_unit_val);
                    editor.putString("username",username_val);
                    editor.putString("password",password_val);
                    editor.putString("location",apartment_location_val);
                    editor.putString("finalpassword",final_password);
                    editor.commit();
                    startActivity(new Intent(RegistrationLandowner.this,GoogleMapLandowners.class));
                    finish();

                }else if (view.getId() == R.id.btn_search_location_transient){
                   // startActivity(new Intent(RegistrationLandowner.this,GoogleMapLandowners.class));
                    Intent i = new Intent(RegistrationLandowner.this, GoogleMapLandowners.class);
                    startActivity(i);
                }else if (view.getId() == R.id.btn_search_location_bedspace) {
                 //   startActivity(new Intent(RegistrationLandowner.this,GoogleMapLandowners.class));
                    Intent i = new Intent(RegistrationLandowner.this, GoogleMapLandowners.class);
                    startActivity(i);
                }
        }
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();

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
                    startActivity(new Intent(RegistrationLandowner.this,Login.class));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    getSharedPreferences("MyPref",0).edit().clear().commit();
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
    //call this to select image from gallery
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    //converting the image to string for uploading
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}