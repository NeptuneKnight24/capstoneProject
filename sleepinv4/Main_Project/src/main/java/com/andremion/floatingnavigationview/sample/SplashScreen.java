package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        ImageView fadeOutImage = (ImageView) findViewById(R.id.iv_logo);
        Animation startFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        fadeOutImage.startAnimation(startFadeInAnimation);

          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  startActivity(new Intent(SplashScreen.this,Login_layout.class));
                  finish();
              }
          },5000);
    }


}
