package org.pasaacademy.COVID_SOS.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pasaacademy.COVID_SOS.Adapters.HotlineNumbersAdapter;
import org.pasaacademy.COVID_SOS.Models.Contact;
import org.pasaacademy.COVID_SOS.R;
import org.pasaacademy.COVID_SOS.databinding.FragmentHotlineBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Hotline#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Hotline extends Fragment implements HotlineNumbersAdapter.OnContactListner {

    FragmentHotlineBinding binding;

    ArrayList<Contact> contacts;
    HotlineNumbersAdapter adapter;

    private FirebaseDatabase database;

    private final int REQUEST_CALL = 1;
    private String phoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Hotline() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Hotline.
     */
    // TODO: Rename and change types and number of parameters
    public static Hotline newInstance(String param1, String param2) {
        Hotline fragment = new Hotline();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //recycler
        RecyclerView recyclerView = view.findViewById(R.id.hotlineNumbers);


        contacts = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        adapter = new HotlineNumbersAdapter(getActivity(), contacts,this);
        recyclerView.setAdapter(adapter);

        database.getReference().child("Contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contacts.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    Contact contact = snapshot1.getValue(Contact.class);
                    contacts.add(contact);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onContactClick(int position) {
        Contact contact = contacts.get(position);
        phoneNumber = contact.getContactNumber();
        makePhoneCall(phoneNumber);
    }

    public void makePhoneCall(String number){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        } else {
            String dial = "tel:" +number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(phoneNumber);
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}