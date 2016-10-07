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
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class PersonalProfile extends AppCompatActivity implements View.OnTouchListener {
    TextView lastname , firstname , usertype, gender,employmentstatus,contactnumber,emailad;
    private FloatingNavigationView mFloatingNavigationView;
    Button personal_profile,accommodations,board_and_lodging,google_map;
    NavigationView navigationView;
    String fname_con,lname_con,level_con,gender_con,type_con,num_con,email_con,imageURL_con;
    String image_url;
    NetworkImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personal_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //textview references
        lastname =(TextView)findViewById(R.id.tv_lastname);
        firstname =(TextView)findViewById(R.id.tv_firstname);
        usertype =(TextView)findViewById(R.id.tv_user_type);
        gender =(TextView)findViewById(R.id.tv_gender);
        employmentstatus =(TextView)findViewById(R.id.tv_employment_status);
        contactnumber =(TextView)findViewById(R.id.tv_contact_number);
        emailad =(TextView)findViewById(R.id.tv_email_address);

        //network image reference
        profile_pic = (NetworkImageView)findViewById(R.id.iv_profile);

        accommodations = (Button)findViewById(R.id.btn_accomodations_2);
        accommodations.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(PersonalProfile.this,Accomodations.class);
                Bundle bundle = new Bundle();
                bundle.putString("fname",fname_con.toString());
                bundle.putString("lname",lname_con.toString());
                bundle.putString("level",level_con.toString());
                bundle.putString("gender",gender_con.toString());
                bundle.putString("type",type_con.toString());
                bundle.putString("num",num_con.toString());
                bundle.putString("email",email_con.toString());
               // bundle.putString("uploadpath",imageURL_con.toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;

            }
        });
        board_and_lodging = (Button)findViewById(R.id.btn_board_and_lodging_2);
        board_and_lodging.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(PersonalProfile.this,BoardAndLodging.class);
                Bundle bundle = new Bundle();
                bundle.putString("fname",fname_con.toString());
                bundle.putString("lname",lname_con.toString());
                bundle.putString("level",level_con.toString());
                bundle.putString("gender",gender_con.toString());
                bundle.putString("type",type_con.toString());
                bundle.putString("num",num_con.toString());
                bundle.putString("email",email_con.toString());
               // bundle.putString("uploadpath",imageURL_con.toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;
            }
        });

        Bundle bundle = getIntent().getExtras();
        lastname.setText(bundle.getString("lname"));
        firstname.setText(bundle.getString("fname"));
        usertype.setText(bundle.getString("type"));
        gender.setText(bundle.getString("gender"));
        employmentstatus.setText(bundle.getString("level"));
        contactnumber.setText(bundle.getString("num"));
        emailad.setText(bundle.getString("email"));
//        image_url = bundle.getString("uploadpath");
//        ImageLoader imageLoader = MySingletonForImage.getInstance().getImageLoader();
//        profile_pic.setImageUrl(image_url, imageLoader);


        fname_con = firstname.getText().toString();
        lname_con = lastname.getText().toString();
        level_con = employmentstatus.getText().toString();
        gender_con = gender.getText().toString();
        type_con = usertype.getText().toString();
        num_con = contactnumber.getText().toString();
        email_con = emailad.getText().toString();
       // imageURL_con = image_url;


        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = mFloatingNavigationView.getMenu();
                menu.findItem(R.id.nav_personal_profile).setVisible(false);
                mFloatingNavigationView.open();

            }
        });

        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId()== R.id.nav_backhome){
                    Intent intent = new Intent(PersonalProfile.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fname",fname_con.toString());
                    bundle.putString("lname",lname_con.toString());
                    bundle.putString("level",level_con.toString());
                    bundle.putString("gender",gender_con.toString());
                    bundle.putString("type",type_con.toString());
                    bundle.putString("num",num_con.toString());
                    bundle.putString("email",email_con.toString());
//                    bundle.putString("uploadpath",imageURL_con.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                   // startActivity(new Intent(PersonalProfile.this,MainActivity.class));

                } else if(item.getItemId()== R.id.nav_accommodations){
                    Intent intent = new Intent(PersonalProfile.this,Accomodations.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fname",fname_con.toString());
                    bundle.putString("lname",lname_con.toString());
                    bundle.putString("level",level_con.toString());
                    bundle.putString("gender",gender_con.toString());
                    bundle.putString("type",type_con.toString());
                    bundle.putString("num",num_con.toString());
                    bundle.putString("email",email_con.toString());
//                    bundle.putString("uploadpath",imageURL_con.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
               // startActivity(new Intent(PersonalProfile.this,Accomodations.class));
                }else if(item.getItemId()== R.id.nav_board){
                    Intent intent = new Intent(PersonalProfile.this,BoardAndLodging.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fname",fname_con.toString());
                    bundle.putString("lname",lname_con.toString());
                    bundle.putString("level",level_con.toString());
                    bundle.putString("gender",gender_con.toString());
                    bundle.putString("type",type_con.toString());
                    bundle.putString("num",num_con.toString());
                    bundle.putString("email",email_con.toString());
//                    bundle.putString("uploadpath",imageURL_con.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
               // startActivity(new Intent(PersonalProfile.this,BoardAndLodging.class));
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
 //     switch (motionEvent.getAction()) {
   //         case MotionEvent.ACTION_UP:
                //test if what button has been touch
//             if (view.getId() == R.id.btn_accommodations) {
                //    Toast.makeText(this, accommodations_text.getText(), Toast.LENGTH_SHORT).show();
//                 Intent intent = new Intent(PersonalProfile.this,Accomodations.class);
//                 Bundle bundle = new Bundle();
//                 bundle.putString("fname",fname_con.toString());
//                 bundle.putString("lname",lname_con.toString());
//                 bundle.putString("level",level_con.toString());
//                 bundle.putString("gender",gender_con.toString());
//                 bundle.putString("type",type_con.toString());
//                 bundle.putString("num",num_con.toString());
//                 bundle.putString("email",email_con.toString());
//                 intent.putExtras(bundle);
//                 startActivity(intent);
//                 finish();
//                } else if (view.getId() == R.id.btn_board_and_lodging) {
//                //    Toast.makeText(this, board_and_lodging_text.getText(), Toast.LENGTH_SHORT).show();
//                 Intent intent = new Intent(PersonalProfile.this,BoardAndLodging.class);
//                 Bundle bundle = new Bundle();
//                 bundle.putString("fname",fname_con.toString());
//                 bundle.putString("lname",lname_con.toString());
//                 bundle.putString("level",level_con.toString());
//                 bundle.putString("gender",gender_con.toString());
//                 bundle.putString("type",type_con.toString());
//                 bundle.putString("num",num_con.toString());
//                 bundle.putString("email",email_con.toString());
//                 intent.putExtras(bundle);
//                 startActivity(intent);
//                 finish();
//                }
  //      }
        return true;
    }

}
