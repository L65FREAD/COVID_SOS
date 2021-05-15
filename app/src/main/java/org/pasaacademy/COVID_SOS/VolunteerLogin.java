package org.pasaacademy.COVID_SOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pasaacademy.COVID_SOS.Models.HospitalModel;
import org.pasaacademy.COVID_SOS.databinding.ActivityVolunteerLoginBinding;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class VolunteerLogin extends AppCompatActivity {

    FirebaseDatabase database;

    ActivityVolunteerLoginBinding binding;
    ProgressDialog progressDialog;

    private static final String PASSWORD_STARTING = "=WCy7P";
    private boolean debounce = true;

    private String name = "", password = "", actualPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVolunteerLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!debounce) {
            Intent intent = new Intent(getApplicationContext(), VolunteerDashboard.class);
            startActivity(intent);
            finish();
        }

        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(VolunteerLogin.this);

        getSupportActionBar().hide();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = binding.email.getEditText().getText().toString();
                password = binding.password.getEditText().getText().toString();

                if (loginUser()) {
                    progressDialog.setTitle("Loging In");
                    progressDialog.setMessage("Please wait a few seconds...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    database.getReference().child("Hospitals").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int i = 0;
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                HospitalModel hospital = snapshot1.getValue(HospitalModel.class);
                                if (hospital.getHospitalName().equalsIgnoreCase(name)) {
                                    StringBuilder sb = new StringBuilder(hospital.getContactNumber());
                                    actualPassword = PASSWORD_STARTING + sb.reverse();
                                    actualPassword = actualPassword.substring(0, 8);
                                    if (password.equals(actualPassword)) {
                                        progressDialog.hide();

                                        debounce = true;
                                        Intent intent = new Intent(getApplicationContext(), VolunteerDashboard.class);
                                        intent.putExtra("position", i);
                                        intent.putExtra("name", hospital.getHospitalName());
                                        intent.setFlags(FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.hide();
                                        binding.password.setError("Invalid Username");
                                    }
                                } else {
                                    progressDialog.hide();
                                    binding.email.setError("Invalid Password");

                                }
                                i++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    Toast.makeText(VolunteerLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private boolean loginUser() {
        if (!checkConnection()) {
            return false;
        }
        if (!validEmail() && !validPassword()) {
            return false;
        }
        return true;
    }

    private boolean validEmail() {
        if (name.isEmpty()) {
            binding.email.setError("Invalid Username");
            return false;
        } else {
            binding.email.setError(null);
            binding.email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validPassword() {

        if (password.isEmpty()) {
            binding.password.setError("Invalid Password");
            return false;
        } else {
            binding.password.setError(null);
            binding.password.setErrorEnabled(false);
            return true;
        }
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