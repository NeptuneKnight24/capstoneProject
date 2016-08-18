package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        ImageView fadeOutImage = (ImageView) findViewById(R.id.iv_logo);
        ImageView cloudone = (ImageView) findViewById(R.id.iv_cloud_one);
        ImageView cloudtwo = (ImageView) findViewById(R.id.iv_cloud_two);
        ImageView cloudthree = (ImageView) findViewById(R.id.iv_cloud_three);
        ImageView cloudfour = (ImageView) findViewById(R.id.iv_cloud_four);
        ImageView cloudfive = (ImageView) findViewById(R.id.iv_cloud_five);
        ImageView cloudsix = (ImageView) findViewById(R.id.iv_cloud_six);


        Animation startFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        fadeOutImage.startAnimation(startFadeInAnimation);

        TranslateAnimation anim_one = new TranslateAnimation(-100f, 10f, 0f, 0f);  // might need to review the docs
        anim_one.setDuration(5500); // set how long you want the animation
        cloudone.setAnimation(anim_one);
        TranslateAnimation anim_two = new TranslateAnimation(-100f, 50f, 0f, 0f);  // might need to review the docs
        anim_two.setDuration(5500);
        cloudtwo.setAnimation(anim_two);
        TranslateAnimation anim_three= new TranslateAnimation(-300f, 10f, 0f, 0f);  // might need to review the docs
        anim_three.setDuration(5500);
        cloudthree.setAnimation(anim_three);
        TranslateAnimation anim_four= new TranslateAnimation(250f, 25f, 0f, 0f);  // might need to review the docs
        anim_four.setDuration(5500);
        cloudfour.setAnimation(anim_four);
        TranslateAnimation anim_five= new TranslateAnimation(350f, 20f, 0f, 0f);  // might need to review the docs
        anim_five.setDuration(5500);
        cloudfive.setAnimation(anim_five);
        TranslateAnimation anim_six= new TranslateAnimation(450f, 40f, 0f, 0f);  // might need to review the docs
        anim_six.setDuration(5500);
        cloudsix.setAnimation(anim_six);

          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  startActivity(new Intent(SplashScreen.this,Login_layout.class));
                  finish();
              }
          },4700);
    }


}
