package org.pasaacademy.COVID_SOS.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.pasaacademy.COVID_SOS.Models.HospitalModel;
import org.pasaacademy.COVID_SOS.R;
import org.pasaacademy.COVID_SOS.databinding.HospitalRecyclerLayoutBinding;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private ArrayList<HospitalModel> hospitalModels;
    private Context context;
    private onHospitalClick onContact;

    public HospitalAdapter(ArrayList<HospitalModel> hospitalModels, Context context, onHospitalClick onContact) {
        this.hospitalModels = hospitalModels;
        this.context = context;
        this.onContact = onContact;
    }

    @NonNull
    @Override
    public HospitalAdapter.HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_recycler_layout, parent, false);
        return new HospitalAdapter.HospitalViewHolder(view, onContact);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.HospitalViewHolder holder, int position) {
        HospitalModel hospitalModel = hospitalModels.get(position);
        holder.binding.hospitalName.setText(hospitalModel.getHospitalName());
        holder.binding.bedsAvailable.setText("Available Beds : "+hospitalModel.getAvailableBeds());
    }

    @Override
    public int getItemCount() {
        return hospitalModels.size();
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        HospitalRecyclerLayoutBinding binding;
        onHospitalClick onHospitalClick;

        public HospitalViewHolder(@NonNull View itemView, onHospitalClick onHospitalClick) {
            super(itemView);
            binding = HospitalRecyclerLayoutBinding.bind(itemView);
            this.onHospitalClick = onHospitalClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHospitalClick.onHospitalClick(getAdapterPosition());
        }


    }

    public interface onHospitalClick {
        void onHospitalClick(int position);
    }



}
