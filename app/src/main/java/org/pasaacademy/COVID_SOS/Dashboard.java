package org.pasaacademy.COVID_SOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.pasaacademy.COVID_SOS.databinding.ActivityDashboardBinding;
import org.pasaacademy.COVID_SOS.Fragments.Beds;
import org.pasaacademy.COVID_SOS.Fragments.Hotline;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {

    ActivityDashboardBinding binding;
    boolean debounce;
    String location;

    //listens for items selected in the navigation view
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_beds:
                            openFragment(Beds.newInstance("", ""));
                            Beds.location = location;
                            return true;
                        case R.id.nav_hotline:
                            if (debounce) {
                                Toast.makeText(Dashboard.this, "Please Wait!", Toast.LENGTH_SHORT).show();
                                debounce = false;
                            }
                            Hotline.location = location;
                            openFragment(Hotline.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();
        location = getIntent().getStringExtra("Location");

        //this makes it so that it will only toast Please wait once
        debounce = true;

        Toast.makeText(Dashboard.this, "Hospitals may take time to load.", Toast.LENGTH_SHORT).show();

        //assign the listener to the navigation view
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(Beds.newInstance("", ""));
    }

    //open a selected fragment
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}