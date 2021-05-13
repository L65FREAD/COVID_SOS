package org.pasaacademy.COVID_SOS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.pasaacademy.COVID_SOS.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity {

    ActivityLoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //hiding the action bar
        getSupportActionBar().hide();

        //go to the main screen(normal user)
        binding.continueToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection()) {
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginScreen.this, "Internet Connection Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //go to login screen for volunteer
        binding.volunteerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection()) {
                    Intent intent = new Intent(getApplicationContext(), VolunteerLogin.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginScreen.this, "Internet Connection Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "Not connected to Network", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}