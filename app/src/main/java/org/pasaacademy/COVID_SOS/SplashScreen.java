package org.pasaacademy.COVID_SOS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;

import org.pasaacademy.COVID_SOS.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding activitySplashScreenBinding;


    private final int SPLASH_SCREEN = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(activitySplashScreenBinding.getRoot());

       getSupportActionBar().hide();

        //Going to the next screen with a delayed time
        //The pairs sends the data about the logo, so there is an animation to fit it in the login screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent goToDashboard = new Intent(SplashScreen.this, LoginScreen.class );
                Pair pairs = new Pair<View,String>(activitySplashScreenBinding.logo,"logo");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                startActivity(goToDashboard,activityOptions.toBundle());
                finish();
            }
        },SPLASH_SCREEN);
    }
}