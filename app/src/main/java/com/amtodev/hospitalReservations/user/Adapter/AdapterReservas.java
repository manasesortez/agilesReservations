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

public class AdapterReservas extends RecyclerView.Adapter<AdapterReservas.DataViewHolder> {

    private CardView cardView;
    Context context;
    ArrayList<DataReservas> listReservas = new ArrayList<>() ;
    private AdapterReservas.OnReservasListener mOnReservasListener;

    public AdapterReservas(Context context, ArrayList<DataReservas> listReservas, AdapterReservas.OnReservasListener mOnReservasListener){
        this.context = context;
        this.listReservas = listReservas;
        this.mOnReservasListener = mOnReservasListener;
    }

    @NonNull
    @Override
    public AdapterReservas.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_reservations, parent, false);
        return new AdapterReservas.DataViewHolder(itemView, mOnReservasListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReservas.DataViewHolder holder, int position) {
        holder.viewBind(listReservas.get(position));
    }

    @Override
    public int getItemCount() {
        return listReservas.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView ReservacionPaciente,  ReservacionHoraConsulta, ReservacionEstadoPaciente, ReservacionDoctorName, ReservacionEspecialidadName, ReservacionHospitalName;
        AdapterReservas.OnReservasListener mOnReservasListener;

        public DataViewHolder(@NonNull View itemView, OnReservasListener mOnReservasListener) {
            super(itemView);
            ReservacionPaciente = itemView.findViewById(R.id.ReservationPaciente);
            ReservacionHoraConsulta = itemView.findViewById(R.id.ReservationHoraConulta);
            ReservacionEstadoPaciente = itemView.findViewById(R.id.ReservationEstadoPaciente);
            ReservacionDoctorName = itemView.findViewById(R.id.ReservationDoctorName);
            ReservacionEspecialidadName = itemView.findViewById(R.id.ReservationEspecialidadName);
            ReservacionHospitalName = itemView.findViewById(R.id.ReservationHospitalName);
            itemView.setOnClickListener(this);
            this.mOnReservasListener = mOnReservasListener;
        }
        @SuppressLint("SetTextI18n")
        public void viewBind(DataReservas dataReservas) {
            ReservacionPaciente.setText(dataReservas.getReserva_nombre_paciente());
            ReservacionHoraConsulta.setText(dataReservas.getReserva_hora_consulta());
            ReservacionEstadoPaciente.setText("Estado: " + dataReservas.getReserva_estado_paciente());
            ReservacionDoctorName.setText(dataReservas.getDoctor_name());
            ReservacionEspecialidadName.setText(dataReservas.getEspecialidad_name());
            ReservacionHospitalName.setText(dataReservas.getHospital_name());
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onCLick: " + getAdapterPosition());
            mOnReservasListener.onReservasClick(getAdapterPosition());
        }


    }

    public interface OnReservasListener{
        void onReservasClick(int position);
    }
}
