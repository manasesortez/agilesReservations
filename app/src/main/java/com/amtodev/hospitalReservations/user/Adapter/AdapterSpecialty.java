package com.amtodev.hospitalReservations.user.Adapter;

import static com.airbnb.lottie.L.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amtodev.hospitalReservations.R;

import java.util.ArrayList;

public class AdapterSpecialty extends RecyclerView.Adapter<AdapterSpecialty.DataViewHolder> {

    private CardView cardView;
    Context context;
    ArrayList<DataSpecialty> listSpecialty = new ArrayList<>() ;
    private OnSpecialtyListener mOnSpecialtyListener;

    public AdapterSpecialty(Context context, ArrayList<DataSpecialty> listSpecialty, OnSpecialtyListener onSpecialtyListener){
        this.context = context;
        this.listSpecialty = listSpecialty;
        this.mOnSpecialtyListener = onSpecialtyListener;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerspecialty, parent, false);
        return new DataViewHolder(itemView, mOnSpecialtyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.viewBind(listSpecialty.get(position));
    }

    @Override
    public int getItemCount() {
        return listSpecialty.size();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView SpecialtyName, SpecialtyID, HospitalName;
        OnSpecialtyListener onSpecialtyListener;

        public DataViewHolder(@NonNull View itemView, OnSpecialtyListener onSpecialtyListener) {
            super(itemView);
            SpecialtyName = itemView.findViewById(R.id.SpecialtyName);
            SpecialtyID = itemView.findViewById(R.id.SpecialtyID);
            HospitalName = itemView.findViewById(R.id.HospitalName);
            itemView.setOnClickListener(this);
            this.onSpecialtyListener = onSpecialtyListener;
        }

        @SuppressLint("SetTextI18n")
        public void viewBind(DataSpecialty dataSpecialty) {
            SpecialtyName.setText(dataSpecialty.getEspecialidad_nombre());
            SpecialtyID.setText("Specialty ID: " + dataSpecialty.getEspecialidad_id());
            HospitalName.setText("From: " + dataSpecialty.getHospital_name());
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onSpecialtyListener.onSpecialtyClick(getAdapterPosition());
        }
    }

    public interface OnSpecialtyListener{
        void onSpecialtyClick(int position);
    }
}
