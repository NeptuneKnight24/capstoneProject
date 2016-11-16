package com.andremion.floatingnavigationview.sample;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Accomodations extends AppCompatActivity implements View.OnTouchListener {

    private FloatingNavigationView mFloatingNavigationView;
    NavigationView navigationView;
    TextView lastname , firstname ,contact,email,apartmentname,apartmentlocation,apartmentunits,
             apartmentfee,label_apartmentfee,label_apartmentrooms,label_apartmentlocation;
    EditText modify_apartment_name_field,modify_apartment_fee_field,modify_apartment_avail_rooms_field,
             modify_landowner_lastname_field,modify_landowner_firstname_field,modify_landowner_number_field,
             modify_landowner_email_field;

    Button modify_details,save_details,cancel_modify,change_apartment_image,change_profile_image,
           upload_building_image,upload_profile_image,cancel_building_upload,cancel_profile_upload;
    String fname_con,lname_con,gender_con,type_con,num_con,email_con,imageURL_con,
            building_name_con,building_available_units_con,building_fee_per_unit_con,building_location_con;
    String image_url="";
    ImageView profile_pic;
    String lastname_val="",firstname_val="",contact_number_val="",email_address_val="";
    //variables for building_name and building permit
    String apartment_name_val="",apartment_business_permit_val="",apartment_number_of_units_val="",
            apartment_fee_per_unit_val="";
    private String KEY_IMAGE_main = "apartment_image";
    private String KEY_IMAGE_profile = "landowner_image";

    private String KEY_IMAGE_preview1 = "preview_image1";
    private String KEY_IMAGE_preview2 = "preview_image2";
    private String KEY_IMAGE_preview3 = "preview_image3";
    String character = "@";

    AlertDialog.Builder builder;
    String edit_details_url = "http://sleepin.comli.com/change_user_details.php";
    String upload_apartment_image_url = "http://sleepin.comli.com/upload_image_building.php";
    String upload_landowner_image_url = "http://sleepin.comli.com/upload_image_landowner.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_accomodations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        label_apartmentfee =(TextView)findViewById(R.id.tv_label_fee_per_unit);
        label_apartmentrooms =(TextView)findViewById(R.id.tv_label_available_unit);
        label_apartmentlocation =(TextView)findViewById(R.id.tv_label_location);

        apartmentname=(TextView)findViewById(R.id.tv_building_name);
        apartmentlocation =(TextView)findViewById(R.id.tv_location);
        apartmentunits =(TextView)findViewById(R.id.tv_unit_available);
        apartmentfee =(TextView)findViewById(R.id.tv_fee);

        firstname =(TextView)findViewById(R.id.tv_landowner_lastname);
        lastname =(TextView)findViewById(R.id.tv_landowner_firstname);
        contact =(TextView)findViewById(R.id.tv_landowner_contact_no);
        email=(TextView)findViewById(R.id.tv_landowner_email);

        modify_apartment_name_field =(EditText)findViewById(R.id.et_modify_apartment_name);
        modify_apartment_fee_field =(EditText)findViewById(R.id.et_modify_apartment_price);
        modify_apartment_avail_rooms_field =(EditText)findViewById(R.id.et_modify_apartment_no_units);

        modify_landowner_firstname_field=(EditText)findViewById(R.id.et_modify_firstname);
        modify_landowner_lastname_field=(EditText)findViewById(R.id.et_modify_lastname);
        modify_landowner_number_field=(EditText)findViewById(R.id.et_modify_contact_number);
        modify_landowner_email_field=(EditText)findViewById(R.id.et_modify_email_address);


        modify_apartment_name_field.setVisibility(View.GONE);
        modify_apartment_fee_field.setVisibility(View.GONE);
        modify_apartment_avail_rooms_field.setVisibility(View.GONE);

        modify_landowner_firstname_field.setVisibility(View.GONE);
        modify_landowner_lastname_field.setVisibility(View.GONE);
        modify_landowner_number_field.setVisibility(View.GONE);
        modify_landowner_email_field.setVisibility(View.GONE);

        change_apartment_image =(Button)findViewById(R.id.btn_change_building_picture);
        change_profile_image =(Button)findViewById(R.id.btn_change_profile_picture);

        upload_profile_image = (Button)findViewById(R.id.btn_upload_profile_picture);
        upload_building_image = (Button)findViewById(R.id.btn_upload_building_picture);

        cancel_building_upload = (Button)findViewById(R.id.btn_cancel_building_upload_picture);
        cancel_profile_upload = (Button)findViewById(R.id.btn_cancel_profile_upload_picture);

        modify_details =(Button)findViewById(R.id.btn_modify_details);
        cancel_modify =(Button)findViewById(R.id.btn_cancel_modify);
        save_details =(Button)findViewById(R.id.btn_save_details);

        save_details.setVisibility(View.GONE);
        cancel_modify.setVisibility(View.GONE);


        modify_details.setOnTouchListener(this);
        cancel_modify.setOnTouchListener(this);

        change_apartment_image.setOnTouchListener(this);
        change_profile_image.setOnTouchListener(this);

        cancel_building_upload.setOnTouchListener(this);
        cancel_profile_upload.setOnTouchListener(this);

        upload_building_image.setOnTouchListener(this);
        upload_profile_image.setOnTouchListener(this);

        Bundle bundle = getIntent().getExtras();
        fname_con = bundle.getString("fname");
        lname_con = bundle.getString("lname");
        gender_con = bundle.getString("gender");
        type_con = bundle.getString("type");
        num_con = bundle.getString("num");
        email_con = bundle.getString("email");
        imageURL_con = bundle.getString("uploadpath");
        building_name_con= bundle.getString("apartmentname");
        building_available_units_con = bundle.getString("apartmentunits");
        building_fee_per_unit_con = bundle.getString("apartmentfee");
        building_location_con= bundle.getString("apartmentlocation");

        lastname.setText(fname_con.toString());
        firstname.setText(lname_con.toString());
        contact.setText(num_con.toString());
        email.setText(email_con.toString());

        apartmentname.setText(building_name_con.toString());
        apartmentfee.setText(building_fee_per_unit_con.toString());
        apartmentunits.setText(building_available_units_con.toString());
        apartmentlocation.setText(building_location_con.toString());

        modify_apartment_name_field.setText(building_name_con.toString());
        modify_apartment_avail_rooms_field.setText(building_available_units_con.toString());
        modify_apartment_fee_field.setText(building_fee_per_unit_con.toString());

        modify_landowner_lastname_field.setText(lname_con.toString());
        modify_landowner_firstname_field.setText(fname_con.toString());
        modify_landowner_number_field.setText(num_con.toString());
        modify_landowner_email_field.setText(email_con.toString());


        upload_building_image.setVisibility(View.GONE);
        cancel_building_upload.setVisibility(View.GONE);

        upload_profile_image.setVisibility(View.GONE);
        cancel_profile_upload.setVisibility(View.GONE);


        profile_pic = (CircleImageView)findViewById(R.id.iv_profile_picture);
        Picasso.with(getApplicationContext()).load(imageURL_con).into(profile_pic);

        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = mFloatingNavigationView.getMenu();
                menu.findItem(R.id.nav_accommodations).setVisible(false);
                mFloatingNavigationView.open();

            }
        });

        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId()== R.id.nav_backhome){
                    Intent intent = new Intent(Accomodations.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fname",fname_con.toString());
                    bundle.putString("lname",lname_con.toString());
                    bundle.putString("gender",gender_con.toString());
                    bundle.putString("type",type_con.toString());
                    bundle.putString("num",num_con.toString());
                    bundle.putString("email",email_con.toString());
                    bundle.putString("uploadpath",imageURL_con.toString());
                    bundle.putString("apartmentname",building_name_con.toString());
                    bundle.putString("apartmentunits",building_available_units_con.toString());
                    bundle.putString("apartmentfee",building_fee_per_unit_con.toString());
                    bundle.putString("apartmentlocation",building_location_con.toString());
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Accomodations.this.finish();
                } else if(item.getItemId()== R.id.nav_personal_profile){
                    Intent intent = new Intent(Accomodations.this,PersonalProfile.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fname",fname_con.toString());
                    bundle.putString("lname",lname_con.toString());
                    bundle.putString("gender",gender_con.toString());
                    bundle.putString("type",type_con.toString());
                    bundle.putString("num",num_con.toString());
                    bundle.putString("email",email_con.toString());
                    bundle.putString("uploadpath",imageURL_con.toString());
                    bundle.putString("apartmentname",building_name_con.toString());
                    bundle.putString("apartmentunits",building_available_units_con.toString());
                    bundle.putString("apartmentfee",building_fee_per_unit_con.toString());
                    bundle.putString("apartmentlocation",building_location_con.toString());
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Accomodations.this.finish();
                }else if(item.getItemId()== R.id.nav_board){
                    Intent intent = new Intent(Accomodations.this,BoardAndLodging.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fname",fname_con.toString());
                    bundle.putString("lname",lname_con.toString());
                    bundle.putString("gender",gender_con.toString());
                    bundle.putString("type",type_con.toString());
                    bundle.putString("num",num_con.toString());
                    bundle.putString("email",email_con.toString());
                    bundle.putString("uploadpath",imageURL_con.toString());
                    bundle.putString("apartmentname",building_name_con.toString());
                    bundle.putString("apartmentunits",building_available_units_con.toString());
                    bundle.putString("apartmentfee",building_fee_per_unit_con.toString());
                    bundle.putString("apartmentlocation",building_location_con.toString());
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Accomodations.this.finish();
                } else if(item.getItemId()== R.id.nav_log_out){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Accomodations.this.finish();
                }
                //Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
                mFloatingNavigationView.close();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //set the values for the variables for textfields
        lastname_val = modify_landowner_lastname_field.getText().toString();
        firstname_val = modify_landowner_firstname_field.getText().toString();
        contact_number_val = modify_landowner_number_field.getText().toString();
        email_address_val= modify_landowner_email_field.getText().toString();
        apartment_name_val = modify_apartment_name_field.getText().toString();
        apartment_number_of_units_val = modify_apartment_avail_rooms_field.getText().toString();
        apartment_fee_per_unit_val = modify_apartment_fee_field.getText().toString();

        //test if a button has been touch
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                //test if what button has been touch
                if (view.getId() == R.id.btn_modify_details) {
                    label_apartmentrooms.setVisibility(View.GONE);
                    label_apartmentfee.setVisibility(View.GONE);
                    label_apartmentlocation.setVisibility(View.GONE);

                    apartmentname.setVisibility(View.GONE);
                    apartmentfee.setVisibility(View.GONE);
                    apartmentunits.setVisibility(View.GONE);
                    apartmentlocation.setVisibility(View.GONE);

                    lastname.setVisibility(View.GONE);
                    firstname.setVisibility(View.GONE);
                    contact.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);

                    change_apartment_image.setVisibility(View.GONE);
                    change_profile_image.setVisibility(View.GONE);


                    modify_apartment_name_field.setVisibility(View.VISIBLE);
                    modify_apartment_fee_field.setVisibility(View.VISIBLE);
                    modify_apartment_avail_rooms_field.setVisibility(View.VISIBLE);

                    modify_landowner_firstname_field.setVisibility(View.VISIBLE);
                    modify_landowner_lastname_field.setVisibility(View.VISIBLE);
                    modify_landowner_number_field.setVisibility(View.VISIBLE);
                    modify_landowner_email_field.setVisibility(View.VISIBLE);

                    save_details.setVisibility(View.VISIBLE);
                    cancel_modify.setVisibility(View.VISIBLE);
                    modify_details.setVisibility(View.GONE);
                } else if (view.getId() == R.id.btn_save_details) {
                    //    Toast.makeText(this, accommodations_text.getText(), Toast.LENGTH_SHORT).show();
                    if (lastname_val.trim().isEmpty() || firstname_val.trim().isEmpty() || contact_number_val.trim().isEmpty() ||
                            email_address_val.trim().isEmpty())
                    {
                        builder.setTitle("Something went wrong.....");
                        builder.setMessage("Please fill up all the fields...");
                        displayAlert("input_error");
                    }
                    else if (!email_address_val.contains(character))
                    {
                        builder.setTitle("Something went wrong.....");
                        builder.setMessage("Email address INVALID");
                        modify_landowner_email_field.requestFocus();
                        displayAlert("input_error");
                    }
                    else
                    {
                        {
                            final ProgressDialog loading = ProgressDialog.show(this,"Connecting...","Please wait...",false,false);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, edit_details_url,
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
                                    Toast.makeText(Accomodations.this,"Connection Error",Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<String, String>();
                                    //Converting Bitmap to String
                                    params.put("lname",lastname_val.toUpperCase());
                                    params.put("fname",firstname_val.toUpperCase());
                                    params.put("apartment_name",apartment_name_val.toUpperCase());
                                    params.put("apartment_permit",apartment_business_permit_val);
                                    params.put("apartment_available_space",apartment_number_of_units_val);
                                    params.put("apartment_fee_per_unit",apartment_fee_per_unit_val);
                                    params.put("num",contact_number_val.toUpperCase());
                                    params.put("email",email_address_val.toUpperCase());
                                    return params;

                                }
                            };
                            MySingleton.getInstance(Accomodations.this).addToRequestque(stringRequest);
                        }
                    }
                } else if (view.getId() == R.id.btn_cancel_modify) {
                    label_apartmentrooms.setVisibility(View.VISIBLE);
                    label_apartmentfee.setVisibility(View.VISIBLE);
                    label_apartmentlocation.setVisibility(View.VISIBLE);

                    apartmentname.setVisibility(View.VISIBLE);
                    apartmentfee.setVisibility(View.VISIBLE);
                    apartmentunits.setVisibility(View.VISIBLE);
                    apartmentlocation.setVisibility(View.VISIBLE);

                    lastname.setVisibility(View.VISIBLE);
                    firstname.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);

                    change_apartment_image.setVisibility(View.VISIBLE);
                    change_profile_image.setVisibility(View.VISIBLE);

                    modify_apartment_name_field.setVisibility(View.GONE);
                    modify_apartment_fee_field.setVisibility(View.GONE);
                    modify_apartment_avail_rooms_field.setVisibility(View.GONE);

                    modify_landowner_firstname_field.setVisibility(View.GONE);
                    modify_landowner_lastname_field.setVisibility(View.GONE);
                    modify_landowner_number_field.setVisibility(View.GONE);
                    modify_landowner_email_field.setVisibility(View.GONE);

                    save_details.setVisibility(View.GONE);
                    cancel_modify.setVisibility(View.GONE);
                    modify_details.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.btn_change_building_picture) {
                    upload_building_image.setVisibility(View.VISIBLE);
                    cancel_building_upload.setVisibility(View.VISIBLE);
                    change_apartment_image.setVisibility(View.GONE);
                } else if (view.getId() == R.id.btn_change_profile_picture) {
                    upload_profile_image.setVisibility(View.VISIBLE);
                    cancel_profile_upload.setVisibility(View.VISIBLE);
                    change_profile_image.setVisibility(View.GONE);
                } else if (view.getId() == R.id.btn_cancel_building_upload_picture) {
                    upload_building_image.setVisibility(View.GONE);
                    cancel_building_upload.setVisibility(View.GONE);
                    change_apartment_image.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.btn_cancel_profile_upload_picture) {
                    upload_profile_image.setVisibility(View.GONE);
                    cancel_profile_upload.setVisibility(View.GONE);
                    change_profile_image.setVisibility(View.VISIBLE);
                }
        }
        return true;
    }

    private void displayAlert(final String code) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("input_error")) {
                    modify_apartment_name_field.requestFocus();
                }
                else if (code.equals("reg_success"))
                {
                    startActivity(new Intent(Accomodations.this,Login.class));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    getSharedPreferences("MyPref",0).edit().clear().commit();
                    finish();
                }
                else if (code.equals("reg_failed"))
                {

                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
