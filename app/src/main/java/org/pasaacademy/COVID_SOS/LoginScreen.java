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

import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class LoginScreen extends AppCompatActivity {

    ActivityLoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //hiding the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        //go to the main screen(normal user)
        binding.continueToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection()) {
                    //Intent to go to the Select location activity
                    Intent intent = new Intent(getApplicationContext(), SelectLocation.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    //no connection identified
                    Toast.makeText(LoginScreen.this, "Internet Connection Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //go to login screen for volunteer
        binding.volunteerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection()) {
                    //Intent to go to the Hospital Login activity
                    Intent intent = new Intent(getApplicationContext(), VolunteerLogin.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    //no connection identified
                    Toast.makeText(LoginScreen.this, "Internet Connection Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Checks whether a device is connected to internet or not
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