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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.pasaacademy.COVID_SOS.databinding.ActivityVolunteerLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VolunteerLogin extends AppCompatActivity {

    ActivityVolunteerLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    private String email = " ", password = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVolunteerLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), VolunteerDashboard.class);
            startActivity(intent);
            finish();
        }

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(VolunteerLogin.this);

        getSupportActionBar().hide();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = binding.email.getEditText().getText().toString();
                password = binding.password.getEditText().getText().toString();

                if (loginUser()) {
                    progressDialog.setTitle("Loging In");
                    progressDialog.setMessage("Please wait a few seconds...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(VolunteerLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), VolunteerDashboard.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(VolunteerLogin.this, "Couldn't Log In.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
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
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (email.isEmpty()) {
            binding.email.setError("Invalid Email");
            return false;

        } else if (!matcher.matches()) {
            binding.email.setError("Invalid Email");
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

    private boolean userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return true;
        }
        return false;
    }

}