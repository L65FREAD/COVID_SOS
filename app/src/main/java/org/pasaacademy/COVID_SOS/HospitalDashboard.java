package org.pasaacademy.COVID_SOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.pasaacademy.COVID_SOS.databinding.ActivityHospitalDashboardBinding;

public class HospitalDashboard extends AppCompatActivity {

    ActivityHospitalDashboardBinding binding;
    private String hospitalName;
    private String hospitalAddress;
    private String availableBeds;
    private String acceptingPatients;
    private String oxygenAvailable;
    private String lastUpdated;
    private String contactNumber;
    private String location;
    private String icuAvailable;
    private String ventilatorAvailable;

    private final int REQUEST_CALL = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHospitalDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        hospitalName = getIntent().getStringExtra("name");
        hospitalAddress = getIntent().getStringExtra("address");
        contactNumber = getIntent().getStringExtra("number");
        availableBeds = getIntent().getStringExtra("availableBeds");
        lastUpdated = getIntent().getStringExtra("lastUpdated");
        oxygenAvailable = getIntent().getStringExtra("oxygenAvailable");
        location = getIntent().getStringExtra("location");
        acceptingPatients = getIntent().getStringExtra("acceptingPatients");
        icuAvailable = getIntent().getStringExtra("icuAvailable");
        ventilatorAvailable = getIntent().getStringExtra("ventilatorAvailable");

        binding.nameHospital.setText(hospitalName);
        binding.addressHospital.setText(hospitalAddress);
        binding.acceptingPatients.setText("Accepting Patients :" + acceptingPatients);
        binding.bedsCount.setText("Beds Available : " + availableBeds);
        binding.oxygenAvailable.setText("Oxygen Available : " + oxygenAvailable);
        binding.lastUpdateTime.setText("Last Updated : "+lastUpdated);
        binding.phoneNumber.setText(contactNumber);
        binding.icuAvailable.setText("ICU Available :" + icuAvailable );
        binding.ventilatorsAvailable.setText("Ventilator Available : "+ventilatorAvailable);

        binding.callHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(contactNumber);
            }
        });

        binding.phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(contactNumber);
            }
        });

    }

    public void makePhoneCall(String number) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(contactNumber);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}