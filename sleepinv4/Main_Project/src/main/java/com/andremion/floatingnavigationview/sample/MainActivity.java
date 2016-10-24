package com.andremion.floatingnavigationview.sample;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private FloatingNavigationView mFloatingNavigationView;
    Spinner occupation_spinner;
    AlertDialog.Builder builder;

    Button personal_profile,accommodations,board_and_lodging,show_info;
    TextView personal_profile_text,accommodations_text,board_and_lodging_text,user_name;
    String fname_con,lname_con,gender_con,type_con,num_con,email_con,imageURL_con,
            building_name_con,building_available_units_con,building_fee_per_unit_con,building_location_con;
    String help_message="Here are some clear descriptions\n" + "\n"+
            "TAP on the PERSONAL PROFILE button to view your account details.\n"+ "\n"+
            "TAP on the BOARD AND LODGING button to view the map and fellow registered landowners.\n"+ "\n"+
            "TAP on the ACCOMMODATIONS button to edit your account details or add preview images for your apartment.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        builder = new AlertDialog.Builder(MainActivity.this);


        personal_profile = (Button)findViewById(R.id.btn_personal_profile);
        accommodations = (Button)findViewById(R.id.btn_accommodations);
        board_and_lodging = (Button)findViewById(R.id.btn_board_and_lodging);
        show_info = (Button)findViewById(R.id.btn_info);



        personal_profile.setOnTouchListener(this);
        accommodations.setOnTouchListener(this);
        board_and_lodging.setOnTouchListener(this);
        show_info.setOnTouchListener(this);


        personal_profile_text = (TextView) findViewById(R.id.tv_pp);
        accommodations_text = (TextView) findViewById(R.id.tv_accom);
        board_and_lodging_text = (TextView) findViewById(R.id.tv_bandl);
        user_name=(TextView)findViewById(R.id.tv_username);

        Bundle bundle = getIntent().getExtras();
        user_name.setText(bundle.getString("fname"));
        fname_con = bundle.getString("fname");
        lname_con = bundle.getString("lname");
        gender_con = bundle.getString("gender");
        type_con = bundle.getString("type");
        num_con = bundle.getString("num");
        email_con = bundle.getString("email");
        imageURL_con = bundle.getString("uploadpath");
        building_name_con = bundle.getString("apartmentname");
        building_available_units_con = bundle.getString("apartmentunits");
        building_fee_per_unit_con = bundle.getString("apartmentfee");
        building_location_con= bundle.getString("apartmentlocation");




        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = mFloatingNavigationView.getMenu();
                menu.findItem(R.id.nav_backhome).setVisible(false);
                mFloatingNavigationView.open();


            }
        });
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId()== R.id.nav_personal_profile){
                    Intent intent = new Intent(MainActivity.this,PersonalProfile.class);
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
                    finish();
                }else if(item.getItemId()== R.id.nav_accommodations){
                    Intent intent = new Intent(MainActivity.this,Accomodations.class);
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
                    finish();
                }else if(item.getItemId()== R.id.nav_board){
                    Intent intent = new Intent(MainActivity.this,BoardAndLodging.class);
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
                    finish();
                }else if(item.getItemId()== R.id.nav_log_out){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
               // Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
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
                if (view.getId() == R.id.btn_personal_profile) {
                    Toast.makeText(this, personal_profile_text.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,PersonalProfile.class);
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
                    finish();
                } else if (view.getId() == R.id.btn_accommodations) {
                    Toast.makeText(this, accommodations_text.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Accomodations.class);
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
                    finish();
                } else if (view.getId() == R.id.btn_board_and_lodging) {
                    Toast.makeText(this, board_and_lodging_text.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,BoardAndLodging.class);
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
                    finish();
                    //startActivity(new Intent(MainActivity.this, BoardAndLodging.class));
                }else if(view.getId() == R.id.btn_info) {
                    builder.setTitle("What does those buttons do?");
                    displayAlert(help_message);
               }
        }
        return true;
    }
    private void displayAlert(final String message) {
        builder.setMessage(message);
        builder.setPositiveButton("THANKS!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
