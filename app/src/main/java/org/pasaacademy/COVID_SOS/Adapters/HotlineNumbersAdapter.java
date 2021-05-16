package org.pasaacademy.COVID_SOS.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.pasaacademy.COVID_SOS.Models.Contact;
import org.pasaacademy.COVID_SOS.R;
import org.pasaacademy.COVID_SOS.databinding.HotlineNumbersLayoutBinding;

import java.util.ArrayList;

public class HotlineNumbersAdapter extends RecyclerView.Adapter<HotlineNumbersAdapter.HotlineViewHolder> {

    Context context;
    ArrayList<Contact> contacts;
    private OnContactListner onContactListner;

    public HotlineNumbersAdapter(Context context, ArrayList<Contact> contacts, OnContactListner onContactListner){
        this.context = context;
        this.contacts = contacts;
        this.onContactListner = onContactListner;

    }

    @NonNull
    @Override
    public HotlineNumbersAdapter.HotlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotline_numbers_layout,parent,false);
        return new HotlineViewHolder(view, onContactListner);
    }

    //Alter the content on the layout
    @Override
    public void onBindViewHolder(@NonNull HotlineNumbersAdapter.HotlineViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.binding.contactName.setText(contact.getContactName());
        holder.binding.contactNumber.setText(contact.getContactNumber());

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class HotlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        HotlineNumbersLayoutBinding binding;
        OnContactListner onContactClick;

        public HotlineViewHolder(@NonNull View itemView, OnContactListner onContactClick) {
            super(itemView);
            binding = HotlineNumbersLayoutBinding.bind(itemView);
            this.onContactClick = onContactClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onContactClick.onContactClick(getAdapterPosition());
        }
    }

    //listens for click on the recycler view and returns the position
    public interface OnContactListner{
        void onContactClick(int position);
    }
}
