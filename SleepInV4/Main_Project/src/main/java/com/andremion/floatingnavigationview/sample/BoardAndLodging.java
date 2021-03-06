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

import com.andremion.floatingnavigationview.FloatingNavigationView;

public class BoardAndLodging extends AppCompatActivity implements View.OnTouchListener {

    private FloatingNavigationView mFloatingNavigationView;
    NavigationView navigationView;
    String fname_con,lname_con,gender_con,type_con,num_con,email_con,imageURL_con,
            building_name_con,building_available_units_con,building_fee_per_unit_con,building_location_con;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board_and_lodging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        fname_con = bundle.getString("fname");
        lname_con = bundle.getString("lname");
        gender_con = bundle.getString("gender");
        type_con = bundle.getString("type");
        num_con = bundle.getString("num");
        email_con = bundle.getString("email");
        imageURL_con= bundle.getString("uploadpath");
        building_name_con= bundle.getString("apartmentname");
        building_available_units_con = bundle.getString("apartmentunits");
        building_fee_per_unit_con = bundle.getString("apartmentfee");
        building_location_con= bundle.getString("apartmentlocation");

        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = mFloatingNavigationView.getMenu();
                menu.findItem(R.id.nav_board).setVisible(false);
                mFloatingNavigationView.open();

            }
        });

        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId()== R.id.nav_backhome){
                    Intent intent = new Intent(BoardAndLodging.this,MainActivity.class);
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
                    BoardAndLodging.this.finish();
                } else if(item.getItemId()== R.id.nav_accommodations){
                    Intent intent = new Intent(BoardAndLodging.this,Accomodations.class);
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
                    BoardAndLodging.this.finish();
                }else if(item.getItemId()== R.id.nav_personal_profile){
                   // startActivity(new Intent(BoardAndLodging.this,BoardAndLodging.class));
                    Intent intent = new Intent(BoardAndLodging.this,PersonalProfile.class);
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
                    BoardAndLodging.this.finish();
                } else if(item.getItemId()== R.id.nav_log_out){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    BoardAndLodging.this.finish();
            }
              //  Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
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
      /*  switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                //test if what button has been touch
                if (view.getId() == R.id.btn_personal_profile) {
             //       Toast.makeText(this, personal_profile_text.getText(), Toast.LENGTH_SHORT).show();
                } else if (view.getId() == R.id.btn_accommodations) {
                //    Toast.makeText(this, accommodations_text.getText(), Toast.LENGTH_SHORT).show();
                } else if (view.getId() == R.id.btn_board_and_lodging) {
                //    Toast.makeText(this, board_and_lodging_text.getText(), Toast.LENGTH_SHORT).show();
                } else if (view.getId() == R.id.btn_google_map) {
             //       Toast.makeText(this, google_map_text.getText(), Toast.LENGTH_SHORT).show();
                }
        }*/
        return true;
    }

}
