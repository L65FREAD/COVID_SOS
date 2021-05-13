package org.pasaacademy.COVID_SOS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.pasaacademy.COVID_SOS.databinding.ActivityVolunteerDashboardBinding;

public class VolunteerDashboard extends AppCompatActivity {

    ActivityVolunteerDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVolunteerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
    }
}