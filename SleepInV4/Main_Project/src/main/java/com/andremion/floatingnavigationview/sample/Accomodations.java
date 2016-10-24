package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Accomodations extends AppCompatActivity implements View.OnTouchListener {

    private FloatingNavigationView mFloatingNavigationView;
    NavigationView navigationView;
    TextView lastname , firstname ,contact,email,apartmentname,apartmentlocation,apartmentunits,
             apartmentfee,label_apartmentfee,label_apartmentrooms,label_apartmentlocation;
    EditText modify_apartment_name_field,modify_apartment_fee_field,modify_apartment_avail_rooms_field,
             modify_landowner_lastname_field,modify_landowner_firstname_field,modify_landowner_number_field,
             modify_landowner_email_field;

    Button modify_details,save_details,cancel_modify;
    String fname_con,lname_con,gender_con,type_con,num_con,email_con,imageURL_con,
            building_name_con,building_available_units_con,building_fee_per_unit_con,building_location_con;
    String image_url="";
    ImageView profile_pic;

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

        modify_details =(Button)findViewById(R.id.btn_modify_details);
        cancel_modify =(Button)findViewById(R.id.btn_cancel_modify);
        save_details =(Button)findViewById(R.id.btn_save_details);
        save_details.setVisibility(View.GONE);
        cancel_modify.setVisibility(View.GONE);


        modify_details.setOnTouchListener(this);
        cancel_modify.setOnTouchListener(this);

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
                   // startActivity(new Intent(Accomodations.this,PersonalProfile.class));
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
                }
        }
        return true;
    }
}
