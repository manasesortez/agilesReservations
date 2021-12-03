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

public class AdapterDoctorInfo extends RecyclerView.Adapter<AdapterDoctorInfo.DataViewHolder>{

    private CardView cardView;
    Context context;
    ArrayList<DataDoctorInfo> listDoctorInfo = new ArrayList<>() ;
    private AdapterDoctorInfo.OnDoctorInfoListener mOnDoctorInfoListener;

    public AdapterDoctorInfo(Context context, ArrayList<DataDoctorInfo> listDoctorInfo, AdapterDoctorInfo.OnDoctorInfoListener mOnDoctorInfoListener){
        this.context = context;
        this.listDoctorInfo = listDoctorInfo;
        this.mOnDoctorInfoListener =mOnDoctorInfoListener;
    }

    @NonNull
    @Override
    public AdapterDoctorInfo.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewdoctor_info, parent, false);
        return new AdapterDoctorInfo.DataViewHolder(itemView, mOnDoctorInfoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoctorInfo.DataViewHolder holder, int position) {
        holder.viewBind(listDoctorInfo.get(position));

    }

    @Override
    public int getItemCount() {
        return listDoctorInfo.size();
    }


    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView DrNameInfo, hospitalNameInfo, specialtyHospitalInfo;
        AdapterDoctorInfo.OnDoctorInfoListener mOnDoctorInfoListener;

        public DataViewHolder(@NonNull View itemView, OnDoctorInfoListener mOnDoctorInfoListener) {
            super(itemView);
            DrNameInfo = itemView.findViewById(R.id.DrNameInfo);
            hospitalNameInfo = itemView.findViewById(R.id.HospitalNameInfo);
            specialtyHospitalInfo = itemView.findViewById(R.id.SpecialtyNameInfo);
            itemView.setOnClickListener(this);
            this.mOnDoctorInfoListener = mOnDoctorInfoListener;
        }
        @SuppressLint("SetTextI18n")
        public void viewBind(DataDoctorInfo dataDoctorInfo) {
            DrNameInfo.setText("Doctor: " + dataDoctorInfo.getDoctor_name());
            hospitalNameInfo.setText("Hospital: " + dataDoctorInfo.getHospital_name());
            specialtyHospitalInfo.setText("Specialty: " + dataDoctorInfo.getEspecialidad_name());
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onCLick: " + getAdapterPosition());
            mOnDoctorInfoListener.onDoctorInfoClick(getAdapterPosition());
        }

    }

    public interface OnDoctorInfoListener{
        void onDoctorInfoClick(int position);
    }
}
