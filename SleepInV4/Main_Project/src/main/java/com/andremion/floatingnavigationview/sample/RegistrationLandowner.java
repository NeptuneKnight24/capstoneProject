package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    LinearLayout apartment_group,transient_group,bedspace_group,total_unit_group;
    LinearLayout apartment_location,transient_location,bedspace_location;
    CheckBox apartment,bedspace,transients;
    TextView back_2_login_tv,landowner_label,perinfo_label,landinfo_label,userinfo_label;//np_test;
    Button register_btn,back_to_choice_btn,select_image;
    Button search_location_apartment,search_location_transient,search_location_bedspace;
    EditText lastname_et,firstname_et,middlename_et,contact_number_et,email_address_et, username_et,password_et,confirm_password_et;
    //variables for textfield values
    String lastname_val="",firstname_val="",middlename_val="",contact_number_val="",email_address_val="",
            username_val="",password_val="", final_password="",unit_type_val="Apartment",business_permit_val="82482",fee_per_unit_val="9000",land_address_val="Dagupan",sex_type_val="",random_id="",
            unit_numbers_val="",unit_numbers_val_final="";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
   // ScrollView view_container;
    AlertDialog.Builder builder;
    String reg_url = "http://sleepin.comli.com/register-landowner.php";
    int i1;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_landowner_layout);

        //view references
        apartment_group = (LinearLayout)findViewById(R.id.ll_apartment_group);
        transient_group = (LinearLayout)findViewById(R.id.ll_transient_group);
        bedspace_group = (LinearLayout)findViewById(R.id.ll_bedspace_group);

        apartment_location = (LinearLayout)findViewById(R.id.ll_apartment_location_group);
        transient_location = (LinearLayout)findViewById(R.id.ll_transient_location_group);
        bedspace_location = (LinearLayout)findViewById(R.id.ll_bedspace_location_group);

       // total_unit_group = (LinearLayout)findViewById(R.id.ll_total_units_group);


        //set visibility to gone on activity start
        apartment_group.setVisibility(View.GONE);
        transient_group.setVisibility(View.GONE);
        bedspace_group.setVisibility(View.GONE);

        apartment_location.setVisibility(View.GONE);
        transient_location.setVisibility(View.GONE);
        bedspace_location.setVisibility(View.GONE);

     //   total_unit_group.setVisibility(View.GONE);


        //checkbox reference
        apartment =(CheckBox)findViewById(R.id.cb_apartment);
        apartment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    apartment_group.setVisibility(View.VISIBLE);
                    apartment_location.setVisibility(View.VISIBLE);
                  //  total_unit_group.setVisibility(View.VISIBLE);
                }else{
                    apartment_group.setVisibility(View.GONE);
                    apartment_location.setVisibility(View.GONE);
//                    if (bedspace.isChecked()|| transients.isChecked()){
//                        total_unit_group.setVisibility(View.VISIBLE);
//                    }else{
//                        total_unit_group.setVisibility(View.GONE);
//                    }
                }
            }
        });
        bedspace =(CheckBox)findViewById(R.id.cb_bedspace);
        bedspace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    bedspace_group.setVisibility(View.VISIBLE);
                    bedspace_location.setVisibility(View.VISIBLE);
                    //total_unit_group.setVisibility(View.VISIBLE);
                }else{
                    bedspace_group.setVisibility(View.GONE);
                    bedspace_location.setVisibility(View.GONE);
//                    if (apartment.isChecked()|| transients.isChecked()){
//                        total_unit_group.setVisibility(View.VISIBLE);
//                    }else{
//                        total_unit_group.setVisibility(View.GONE);
//                    }
                }
            }
        });
        transients =(CheckBox)findViewById(R.id.cb_transients);
        transients.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    transient_group.setVisibility(View.VISIBLE);
                    transient_location.setVisibility(View.VISIBLE);
                //    total_unit_group.setVisibility(View.VISIBLE);
                }else{
                   transient_group.setVisibility(View.GONE);
                   transient_location.setVisibility(View.GONE);
//                    if (apartment.isChecked()|| bedspace.isChecked()){
//                        total_unit_group.setVisibility(View.VISIBLE);
//                    }else{
//                        total_unit_group.setVisibility(View.GONE);
//                    }
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


        //textfields reference
        lastname_et=(EditText)findViewById(R.id.et_lastname);
        firstname_et=(EditText)findViewById(R.id.et_firstname);
        middlename_et=(EditText)findViewById(R.id.et_middlename);
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
        //spinner type
       // unit_type_spinner= (Spinner)findViewById(R.id.spinner_unit_type);

        //textview reference
        back_2_login_tv=(TextView)findViewById(R.id.tv_text_goback);
        back_2_login_tv.setTypeface(face);

        //set the onTouchListener to perform actions
        back_2_login_tv.setOnTouchListener(this);


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
    //call this to select image from gallery
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    //set the image to the imageview
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
    //converting the image to string for uploading
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
     //   unit_type_val=unit_type_spinner.getSelectedItem().toString();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
     //   unit_type_val=unit_type_spinner.getSelectedItem().toString();
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
        //unit_numbers_val_final = unit_numbers_val.trim().toString();
        unit_numbers_val_final = "0";
        username_val= username_et.getText().toString();
        password_val=password_et.getText().toString();
        final_password= confirm_password_et.getText().toString();

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
                                    params.put("lname",lastname_val);
                                    params.put("fname",firstname_val);
                                    params.put("midname",middlename_val);
                                    params.put("gender",sex_type_val);
                                    params.put("num",contact_number_val);
                                    params.put("email",email_address_val);
                                    params.put("permit_number",business_permit_val);
                                  // params.put("permit_number",business_permit_val);
                                    params.put("unit_type",unit_type_val);
                                    params.put("available_units",unit_numbers_val_final);
                                    params.put("fee_per_unit",fee_per_unit_val);
                                    params.put("land_address",land_address_val);
                                    params.put("uname",username_val);
                                    params.put("pword",final_password);
                                    params.put(KEY_IMAGE, image);
                                    return params;
                                }
                            };
                            MySingleton.getInstance(RegistrationLandowner.this).addToRequestque(stringRequest);
                          /* Toast.makeText(this, ""+lastname_val+"\n"+firstname_val+"\n"+sex_type_val+"\n"+occupation_stat_val+"\n"+contact_number_val+
                                       ""+email_address_val+"\n"+username_val+"\n"+password_val+"\n"+final_password
                                      , Toast.LENGTH_SHORT).show();*/
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
                    Toast.makeText(this, "Get Location is still in beta and will not work", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationLandowner.this,GoogleMapLandowners.class));
                }else if (view.getId() == R.id.btn_search_location_transient){
                    startActivity(new Intent(RegistrationLandowner.this,GoogleMapLandowners.class));
                    Toast.makeText(this, "Get Location is still in beta and will not work", Toast.LENGTH_SHORT).show();
                }else if (view.getId() == R.id.btn_search_location_bedspace) {
                    startActivity(new Intent(RegistrationLandowner.this,GoogleMapLandowners.class));
                    Toast.makeText(this, "Get Location is still in beta and will not work", Toast.LENGTH_SHORT).show();


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
                    startActivity(new Intent(RegistrationLandowner.this,Login.class));
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