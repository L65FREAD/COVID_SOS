package org.pasaacademy.COVID_SOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pasaacademy.COVID_SOS.Models.HospitalModel;
import org.pasaacademy.COVID_SOS.databinding.ActivityVolunteerDashboardBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class VolunteerDashboard extends AppCompatActivity {

    ActivityVolunteerDashboardBinding binding;
    private int hospitalName;
    HospitalModel model;
    String volunteerName;

    private String availableBeds, availableICU, availableVentilators;
    private String oxygenAvailable, patientsAccepting;

    private DatabaseReference reference;

    @Override
    protected void onResume() {
        super.onResume();
        String[] numbers = getResources().getStringArray(R.array.numbers);
        String[] bool = getResources().getStringArray(R.array.bool);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.dropdownitem, numbers);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getApplication(), R.layout.dropdownitem, bool);
        binding.availableBeds.setAdapter(arrayAdapter);
        binding.availableICU.setAdapter(arrayAdapter);
        binding.availableVentilators.setAdapter(arrayAdapter);
        binding.availableOxygen.setAdapter(arrayAdapter2);
        binding.patientsAccepting.setAdapter(arrayAdapter2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVolunteerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        reference = FirebaseDatabase.getInstance().getReference("Hospitals");

        String name = getIntent().getStringExtra("name");
        hospitalName = getIntent().getIntExtra("position", 0);
        String lastUpdate = getIntent().getStringExtra("time");
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(lastUpdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeMilli = d.getTime();


        binding.hospitalName.setText(name);

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timeMilli + 10000 >= System.currentTimeMillis()) {

                    //update the data
                    availableBeds = binding.availableBeds.getText().toString();
                    availableICU = binding.availableICU.getText().toString();
                    availableVentilators = binding.availableVentilators.getText().toString();
                    oxygenAvailable = binding.availableOxygen.getText().toString();
                    patientsAccepting = binding.patientsAccepting.getText().toString();
                    volunteerName = binding.inputName.getEditText().getText().toString();

                    //getting the time
                    Date currentTime = Calendar.getInstance().getTime();
                    String time = currentTime.toString();
                    char ch;
                    int whiteSpaceCount = 0;
                    int startPosition = 0;
                    for (int i = 0; i < time.length(); i++) {
                        if (whiteSpaceCount == 3) {
                            startPosition = i;
                            break;
                        }
                        ch = time.charAt(i);
                        if (Character.isWhitespace(ch)) {
                            whiteSpaceCount++;
                        }
                    }
                    String actualTime = time.substring(startPosition, startPosition + 8);


                    long beds, icu, ventilators;
                    boolean oxy, patien;

                    if (validateVolunteerName()) {

                        //send reminder in two hours
                        setReminder();

                        if (availableBeds.equals("15+")) {
                            beds = 16;
                        } else {
                            beds = Long.parseLong(availableBeds);
                            reference.child(hospitalName + "").child("availableBeds").setValue(beds);
                        }

                        if (availableVentilators.equals("15+")) {
                            ventilators = 16;
                        } else {
                            ventilators = Long.parseLong(availableBeds);
                            reference.child(hospitalName + "").child("availableVentilators").setValue(ventilators);
                        }

                        if (availableICU.equals("15+")) {
                            icu = 16;
                        } else {
                            icu = Long.parseLong(availableBeds);
                            reference.child(hospitalName + "").child("availableICU").setValue(icu);
                        }

                        oxy = oxygenAvailable.equalsIgnoreCase("Yes");

                        patien = patientsAccepting.equalsIgnoreCase("Yes");

                        reference.child(hospitalName + "").child("acceptingPatients").setValue(patien);
                        reference.child(hospitalName + "").child("oxygenAvailable").setValue(oxy);
                        reference.child(hospitalName + "").child("lastUpdated").setValue(actualTime);

                    }
                }else {
                    Toast.makeText(VolunteerDashboard.this, "Wait before you update again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean validateVolunteerName() {
        if (volunteerName.equals("")) {
            binding.inputName.setError("Can't be empty");
            return false;
        } else {
            binding.inputName.setError(null);
            binding.inputName.setErrorEnabled(false);
            return true;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "VolunteerReminderChannel";
            String description = "Channel for Volunteer Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyVolunteer", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setReminder() {
        createNotificationChannel();
        Intent intent = new Intent(getApplicationContext(), org.pasaacademy.COVID_SOS.Models.NotificationManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        Toast.makeText(VolunteerDashboard.this, "Thanks for updating, you'll be reminded in 2 hours", Toast.LENGTH_SHORT).show();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long timeNow = System.currentTimeMillis();
        long twoHoursInMilli = (long) ((long) 2 * (3.6e+6));
        manager.set(AlarmManager.RTC_WAKEUP, timeNow + twoHoursInMilli, pendingIntent);
    }

}