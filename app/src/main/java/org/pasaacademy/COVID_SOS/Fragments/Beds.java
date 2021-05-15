package org.pasaacademy.COVID_SOS.Fragments;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pasaacademy.COVID_SOS.Adapters.HospitalAdapter;
import org.pasaacademy.COVID_SOS.HospitalDashboard;
import org.pasaacademy.COVID_SOS.Models.CovidCases;
import org.pasaacademy.COVID_SOS.Models.HospitalModel;
import org.pasaacademy.COVID_SOS.R;
import org.pasaacademy.COVID_SOS.databinding.HospitalRecyclerLayoutBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Beds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Beds extends Fragment implements HospitalAdapter.onHospitalClick {

    private FirebaseDatabase database;
    public static String location;
    private HospitalRecyclerLayoutBinding binding;

    ArrayList<HospitalModel> hospitals  = new ArrayList<>();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Beds() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Beds.
     */
    // TODO: Rename and change types and number of parameters
    public static Beds newInstance(String param1, String param2) {
        Beds fragment = new Beds();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Handles the COVID-Stats part
        TextView activeCases = view.findViewById(R.id.activeCases);
        TextView recoveredCases = view.findViewById(R.id.recoveredCases);
        TextView totalCases = view.findViewById(R.id.totalCases);
        TextView lastUpdated = view.findViewById(R.id.lastUpdated);

        database = FirebaseDatabase.getInstance();
        database.getReference().child("Cases").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CovidCases covidCases = snapshot1.getValue(CovidCases.class);
                    activeCases.setText(" :" + covidCases.getActiveCases());
                    lastUpdated.setText("Last Updated : " + covidCases.getLastUpdated());
                    recoveredCases.setText(" :" + covidCases.getRecoveredCases());
                    totalCases.setText(" :" + covidCases.getTotalCases());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.hospitalDetail);
        HospitalAdapter hospitalAdapter = new HospitalAdapter(hospitals, getContext(), this);
        recyclerView.setAdapter(hospitalAdapter);

        //Handles the Hospital Recycler Part
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Hospitals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    HospitalModel hospital = snapshot1.getValue(HospitalModel.class);
                    if (hospital.getLocation().equalsIgnoreCase(location)) {
                        hospitals.add(hospital);
                    }
                }
                hospitalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onHospitalClick(int position) {
        binding = HospitalRecyclerLayoutBinding.inflate(getLayoutInflater());
        //do things when the Recycler view is clicked
        HospitalModel hospitalModel = hospitals.get(position);
        Intent intent = new Intent(getContext(), HospitalDashboard.class);
        intent.putExtra("name", hospitalModel.getHospitalName());
        intent.putExtra("address", hospitalModel.getHospitalAddress());
        intent.putExtra("number", hospitalModel.getContactNumber());
        intent.putExtra("availableBeds", hospitalModel.getAvailableBeds() + "");
        intent.putExtra("lastUpdated", hospitalModel.getLastUpdated());
        intent.putExtra("oxygenAvailable", (hospitalModel.getOxygenAvailable()? "Yes" : "No"));
        intent.putExtra("location", hospitalModel.getLocation());
        intent.putExtra("acceptingPatients", (hospitalModel.getAcceptingPatients() ? "Yes" : "No"));
        intent.putExtra("icuAvailable",(hospitalModel.getIcuAvailable() + ""));
        intent.putExtra("ventilatorAvailable",(hospitalModel.getVentilatorAvailable() + ""));
        startActivity(intent);
    }

}