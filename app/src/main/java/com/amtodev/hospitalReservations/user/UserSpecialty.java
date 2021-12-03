package com.amtodev.hospitalReservations.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.amtodev.hospitalReservations.user.Adapter.AdapterHospital;
import com.amtodev.hospitalReservations.user.Adapter.AdapterSpecialty;
import com.amtodev.hospitalReservations.user.Adapter.DataHospital;
import com.amtodev.hospitalReservations.user.Adapter.DataSpecialty;

import java.util.ArrayList;
import java.util.List;

public class UserSpecialty extends AppCompatActivity implements AdapterSpecialty.OnSpecialtyListener  {

    private static final String TAG = "UserSpecialty";
    ImageButton SearchSpecialty;

    RecyclerView recycleViewSpecialty;
    AdapterSpecialty adapterSpecialty;
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "agilesReservas";
    Configurations objConfiguracion;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    List<Integer> arregloID = new ArrayList<Integer>();
    ArrayList<DataSpecialty> listSpecialty = new ArrayList<>();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_specialty);

        ImageView goBackActivity = findViewById(R.id.btnAfter);
        goBackActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AfterActivity();
            }
        });

        SearchSpecialty = findViewById(R.id.btnSearchUserSpecialty);
        SearchSpecialty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSpecialty.this, "Search Item Specialty", Toast.LENGTH_LONG).show();
            }
        });

        if(getIntent().hasExtra("hospital_id")){
            DataHospital hospital = getIntent().getParcelableExtra("hospital_id");
            int hospital_id = hospital.getHospital_id();
        }

        recycleViewSpecialty = findViewById(R.id.recycleViewSpecialty);

        progressDialog = new ProgressDialog(UserSpecialty.this);
        progressDialog.setCancelable(false);
        objConexion = new ConexionSQLite(UserSpecialty.this, NOMBRE_BASE_DATOS, null, 1);
        showDataSpecialty();
    }

    public void buscar(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        buscar();
    }

    private void showDataSpecialty() {
        try{
            if(getIntent().hasExtra("hospital_id")){
                DataHospital hospitalInfo = getIntent().getParcelableExtra("hospital_id");
                int hospital_id = hospitalInfo.getHospital_id();

                DataSpecialty specialialty;
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Cursor res = objConexion.getShowDataSpecialty(hospital_id);
                listSpecialty.clear();
                while (res.moveToNext()){
                    specialialty = new DataSpecialty(
                            res.getInt(0),
                            res.getString(1),
                            res.getString(2),
                            res.getInt(3)
                    );
                    listSpecialty.add(specialialty);
                }
                adapterSpecialty = new AdapterSpecialty(UserSpecialty.this, listSpecialty, this);
                recycleViewSpecialty.setAdapter(adapterSpecialty);
                progressDialog.dismiss();
            }
        }catch(Exception error){
            Toast.makeText(UserSpecialty.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void AfterActivity(){
        startActivity(new Intent(getApplicationContext(), UserMain.class));
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        UserSpecialty.this.finish();
    }

    @Override
    public void onSpecialtyClick(int position) {
        Intent intent = new Intent(this, UserDoctor.class);
        intent.putExtra("especialidad_id", listSpecialty.get(position));
        startActivity(intent);
    }
}