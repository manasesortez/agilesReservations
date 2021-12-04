package com.amtodev.hospitalReservations.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.user.Adapter.DataDoctor;
import com.amtodev.hospitalReservations.user.Adapter.DataDoctorInfo;

public class ViewReservations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);

        if(getIntent().hasExtra("doctor_id_info")) {
            DataDoctorInfo doctorInfo = getIntent().getParcelableExtra("doctor_id_info");
            int doctor_id = doctorInfo.getDoctor_id();
            Toast.makeText(ViewReservations.this, "Info: " + doctor_id, Toast.LENGTH_LONG).show();
        }
    }
}