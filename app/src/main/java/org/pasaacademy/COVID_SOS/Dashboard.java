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

public class Dashboard extends AppCompatActivity {

    ActivityDashboardBinding binding;
    boolean debounce, rebound;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        location = getIntent().getStringExtra("Location");

        debounce = true;
        rebound = true;

        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(Beds.newInstance("", ""));
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_beds:
                            if (rebound) {
                                Toast.makeText(Dashboard.this, "Hospitals may take time to load.", Toast.LENGTH_SHORT).show();
                                rebound = false;
                            }
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
}