package org.pasaacademy.COVID_SOS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.pasaacademy.COVID_SOS.databinding.ActivitySelectLocationBinding;

public class SelectLocation extends AppCompatActivity {

    ActivitySelectLocationBinding binding;
    private String location;

    @Override
    protected void onResume() {
        super.onResume();
        String[] cities = getResources().getStringArray(R.array.Cities);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.dropdownitem, cities);
        binding.locationDropdown.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        binding.continueLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = binding.locationDropdown.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                intent.putExtra("Location", location);
                startActivity(intent);
                finish();
            }
        });

    }


}