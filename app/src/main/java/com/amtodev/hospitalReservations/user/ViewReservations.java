package com.amtodev.hospitalReservations.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.Class.Configurations;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.user.Adapter.AdapterDoctor;
import com.amtodev.hospitalReservations.user.Adapter.AdapterReservas;
import com.amtodev.hospitalReservations.user.Adapter.DataDoctor;
import com.amtodev.hospitalReservations.user.Adapter.DataDoctorInfo;
import com.amtodev.hospitalReservations.user.Adapter.DataReservas;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ViewReservations extends AppCompatActivity implements AdapterReservas.OnReservasListener {

    private static final String TAG = "ViewReservations";
    ImageButton SearchReservations;
    ImageButton goBackActivityReservations;

    RecyclerView recycleViewReservation;
    AdapterReservas adapterReservas;
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "agilesReservas";
    Configurations objConfiguracion;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    List<Integer> arregloID = new ArrayList<Integer>();
    ArrayList<DataReservas> listReservas = new ArrayList<>();
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);

        if(getIntent().hasExtra("doctor_id_info")) {
            DataDoctorInfo doctorInfo = getIntent().getParcelableExtra("doctor_id_info");
            int doctor_id = doctorInfo.getDoctor_id();
        }

        ImageView btnAfterReservations = findViewById(R.id.btnAfterReservations);
        btnAfterReservations.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AfterActivity();
            }
        });

        ImageButton btnSearchReservation = findViewById(R.id.btnSearchReservation);
        btnSearchReservation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewReservations.this, "Search Reservstions" , Toast.LENGTH_LONG).show();
            }
        });

        recycleViewReservation= findViewById(R.id.recyclerViewReservation);
        progressDialog = new ProgressDialog(ViewReservations.this);
        progressDialog.setCancelable(false);
        objConexion = new ConexionSQLite(ViewReservations.this, NOMBRE_BASE_DATOS, null, 1);
        showDataReservations();
    }

    public void buscar(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        buscar();
    }

    private void showDataReservations() {
        try{
            if(getIntent().hasExtra("doctor_id_info")) {
                DataDoctorInfo doctorInfo = getIntent().getParcelableExtra("doctor_id_info");
                int doctor_id = doctorInfo.getDoctor_id();
                int especialidad_id = doctorInfo.getEspecialidad_id();
                int hospital_id = doctorInfo.getDoctor_id();

                fAuth = FirebaseAuth.getInstance();
                String UserId = fAuth.getCurrentUser().getUid();

                DataReservas reservas;
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Cursor res = objConexion.getShowDatReservations(especialidad_id, hospital_id, doctor_id, UserId);
                listReservas.clear();
                while (res.moveToNext()){
                    reservas = new DataReservas(
                            res.getInt(0),
                            res.getString(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6),
                            res.getInt(7),
                            res.getInt(8),
                            res.getInt(9),
                            res.getString(10)
                    );
                    listReservas.add(reservas);
                }
                adapterReservas = new AdapterReservas(ViewReservations.this, listReservas, this);
                recycleViewReservation.setAdapter(adapterReservas);
                progressDialog.dismiss();
            }
        }catch(Exception error){
            Toast.makeText(ViewReservations.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



    private void AfterActivity() {
        startActivity(new Intent(getApplicationContext(), UserMain.class));
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        ViewReservations.this.finish();
    }

    @Override
    public void onReservasClick(int position) {
    }
}