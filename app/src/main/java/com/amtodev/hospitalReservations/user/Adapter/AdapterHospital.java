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

public class AdapterHospital extends RecyclerView.Adapter<AdapterHospital.DataViewHolder> {
    private CardView cardView;
    Context context;
    ArrayList<DataHospital> listHospital = new ArrayList<>() ;
    private OnHospitalListener mOnHospitalListener;

    public AdapterHospital(Context context, ArrayList<DataHospital> listHospital, OnHospitalListener onHospitalListener) {
        this.context = context;
        this.listHospital = listHospital;
        this.mOnHospitalListener = onHospitalListener;
    }

    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new DataViewHolder(itemView, mOnHospitalListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position){
        holder.viewBind(listHospital.get(position));
    }

    @Override
    public int getItemCount() {
        return listHospital.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_nombreHospital, tv_NumberHospital, tv_Direccion;
        OnHospitalListener onHospitalListener;

        public DataViewHolder(@NonNull View itemView, OnHospitalListener onHospitalListener) {
            super(itemView);
            tv_nombreHospital = itemView.findViewById(R.id.hospitalName);
            tv_NumberHospital = itemView.findViewById(R.id.HospitalNumber);
            tv_Direccion = itemView.findViewById(R.id.HospitalAddress);
            itemView.setOnClickListener(this);
            this.onHospitalListener = onHospitalListener;

        }
        @SuppressLint("SetTextI18n")
        public void viewBind(DataHospital dataHospital) {
            tv_nombreHospital.setText(dataHospital.getHospital_nombre());
            tv_NumberHospital.setText("Numero: " + dataHospital.getHospital_telefono());
            tv_Direccion.setText("Direccion: " + dataHospital.getHospital_direccion());

        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onHospitalListener.onHospitalClick(getAdapterPosition());
        }
    }

    public interface OnHospitalListener{
        void onHospitalClick(int position);
    }
}
