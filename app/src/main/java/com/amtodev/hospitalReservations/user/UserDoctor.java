package com.amtodev.hospitalReservations.user;

import static com.airbnb.lottie.L.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.Class.Configurations;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.user.Adapter.AdapterDoctor;
import com.amtodev.hospitalReservations.user.Adapter.DataDoctor;
import com.amtodev.hospitalReservations.user.Adapter.DataHospital;
import com.amtodev.hospitalReservations.user.Adapter.DataSpecialty;

import java.util.ArrayList;
import java.util.List;

public class UserDoctor extends AppCompatActivity implements AdapterDoctor.OnDoctorListener {

    private static final String TAG = "UserDoctor";
    ImageButton SearchDoctor;

    RecyclerView recycleViewDoctor;
    AdapterDoctor adapterDoctor;
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "agilesReservas";
    Configurations objConfiguracion;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    List<Integer> arregloID = new ArrayList<Integer>();
    ArrayList<DataDoctor> listDoctor = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_doctor);

        ImageView goBackActivity = findViewById(R.id.backSpecialty);
        goBackActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AfterActivity();
            }
        });

        SearchDoctor = findViewById(R.id.btnSearchUserDoctor);
        SearchDoctor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(UserDoctor.this, "Search Item Doctor", Toast.LENGTH_LONG).show();
            }
        });

        if(getIntent().hasExtra("especialidad_id")) {
            DataSpecialty specialty = getIntent().getParcelableExtra("especialidad_id");
            int specialidad_id = specialty.getEspecialidad_id();
        }

        recycleViewDoctor= findViewById(R.id.recycleViewDoctor);

        progressDialog = new ProgressDialog(UserDoctor.this);
        progressDialog.setCancelable(false);
        objConexion = new ConexionSQLite(UserDoctor.this, NOMBRE_BASE_DATOS, null, 1);
        showDataDoctor();
    }

    public void buscar(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        buscar();
    }

    private void showDataDoctor() {
        try{
            if(getIntent().hasExtra("especialidad_id")) {
                DataSpecialty especialtyInfo= getIntent().getParcelableExtra("especialidad_id");
                int especialidad_id = especialtyInfo.getEspecialidad_id();
                int hospital_id = especialtyInfo.getHospital_id();
                Toast.makeText(UserDoctor.this, "Hospital_id: " + hospital_id, Toast.LENGTH_LONG).show();

                DataDoctor doctor;
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Cursor res = objConexion.getShowDataDoctor(especialidad_id, hospital_id);
                listDoctor.clear();
                while (res.moveToNext()){
                    doctor = new DataDoctor(
                            res.getInt(0),
                            res.getString(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6),
                            res.getString(7),
                            res.getString(8),
                            res.getInt(9),
                            res.getInt(10)
                    );
                    listDoctor.add(doctor);
                }
                adapterDoctor = new AdapterDoctor(UserDoctor.this, listDoctor, this);
                recycleViewDoctor.setAdapter(adapterDoctor);
                progressDialog.dismiss();
            }
        }catch(Exception error){
            Toast.makeText(UserDoctor.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void AfterActivity(){
        startActivity(new Intent(getApplicationContext(), UserMain.class));
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        UserDoctor.this.finish();
    }

    @Override
    public void onDoctorClick(int position) {
        Intent intent = new Intent(this, CreateReservations.class);
        intent.putExtra("doctor_id", listDoctor.get(position));
        Toast.makeText(UserDoctor.this, "doctor_id: " + listDoctor.get(position), Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}