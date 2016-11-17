package com.andremion.floatingnavigationview.sample;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

public class AccomodationsForViewing extends AppCompatActivity implements View.OnTouchListener {


    TextView lastname , firstname ,contact,email,apartmentname,apartmentlocation,apartmentunits,
             apartmentfee,label_apartmentfee,label_apartmentrooms,label_apartmentlocation;
    EditText modify_apartment_name_field,modify_apartment_fee_field,modify_apartment_avail_rooms_field,
             modify_landowner_lastname_field,modify_landowner_firstname_field,modify_landowner_number_field,
             modify_landowner_email_field;

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
        setContentView(R.layout.accomodations_for_bedspacers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
