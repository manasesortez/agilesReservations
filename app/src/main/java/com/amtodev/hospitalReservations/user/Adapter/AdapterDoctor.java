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

public class AdapterDoctor extends RecyclerView.Adapter<AdapterDoctor.DataViewHolder>{

    private CardView cardView;
    Context context;
    ArrayList<DataDoctor> listDoctor = new ArrayList<>() ;
    private AdapterDoctor.OnDoctorListener mOnDoctorListener;

    public AdapterDoctor(Context context, ArrayList<DataDoctor> listDoctor, AdapterDoctor.OnDoctorListener mOnDoctorListener){
        this.context = context;
        this.listDoctor = listDoctor;
        this.mOnDoctorListener = mOnDoctorListener;
    }

    @NonNull
    @Override
    public AdapterDoctor.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerdoctor, parent, false);
        return new AdapterDoctor.DataViewHolder(itemView, mOnDoctorListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoctor.DataViewHolder holder, int position) {
        holder.viewBind(listDoctor.get(position));
    }

    @Override
    public int getItemCount() {
        return listDoctor.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView DoctorID,  DoctorName, HospitalNameDoctor, SpecialtyNameDoctor, DireccionDoctor, CostoDoctor, DiasHabilesDoctor, horaEntradaDoctor, horaSalidaDoctor;
        AdapterDoctor.OnDoctorListener onDoctorListener;

        public DataViewHolder(@NonNull View itemView, OnDoctorListener onDoctorListener) {
            super(itemView);
            DoctorID = itemView.findViewById(R.id.DoctorID);
            DoctorName = itemView.findViewById(R.id.DoctorName);
            HospitalNameDoctor = itemView.findViewById(R.id.HospitalNameDoctor);
            SpecialtyNameDoctor = itemView.findViewById(R.id.SpecialtyNameDoctor);
            DireccionDoctor = itemView.findViewById(R.id.DireccionDoctor);
            CostoDoctor = itemView.findViewById(R.id.CostoDoctor);
            DiasHabilesDoctor = itemView.findViewById(R.id.DiasHabilesDoctor);
            horaEntradaDoctor = itemView.findViewById(R.id.horaEntradaDoctor);
            horaSalidaDoctor = itemView.findViewById(R.id.horaSalidaDoctor);
            itemView.setOnClickListener(this);
            this.onDoctorListener = onDoctorListener;
        }

        @SuppressLint("SetTextI18n")
        public void viewBind(DataDoctor dataDoctor) {
            DoctorName.setText(dataDoctor.getDoctor_name());
            HospitalNameDoctor.setText(dataDoctor.getHospital_name());
            SpecialtyNameDoctor.setText("From: " + dataDoctor.getEspecialidad_name());
            DireccionDoctor.setText("Direccion: " + dataDoctor.getDoctor_direccion());
            CostoDoctor.setText("Costo: $" + dataDoctor.getDoctor_costo_consulta());
            DiasHabilesDoctor.setText("Dias Habiles: " + dataDoctor.getDoctor_dias_habiles());
            horaEntradaDoctor.setText("Hora de Entrada: " + dataDoctor.getDoctor_hora_entrada());
            horaSalidaDoctor.setText("Hora Salida: " + dataDoctor.getDoctor_hora_salida());
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onCLick: " + getAdapterPosition());
            onDoctorListener.onDoctorClick(getAdapterPosition());
        }
    }

    public interface OnDoctorListener{
        void onDoctorClick(int position);
    }


}
