package com.amtodev.hospitalReservations.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.Class.Configurations;
import com.amtodev.hospitalReservations.Login;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Hospital.AddHospital;
import com.amtodev.hospitalReservations.admin.Hospital.ViewHospital;
import com.amtodev.hospitalReservations.user.Adapter.AdapterHospital;
import com.amtodev.hospitalReservations.user.Adapter.DataHospital;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class UserMain extends AppCompatActivity {

    RecyclerView recycleViewHospital;
    AdapterHospital adapterHospital;

    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "agilesReservas";
    Configurations objConfiguracion;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    List<Integer> arregloID = new ArrayList<Integer>();
    ArrayList<DataHospital> listHospital = new ArrayList<>();
    ProgressDialog progressDialog;

    ImageButton SearchHospital;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        recycleViewHospital = findViewById(R.id.recycleViewHospital);

        ImageView logoutUser = findViewById(R.id.buttonLoggoutUser);
        logoutUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });

        progressDialog = new ProgressDialog(UserMain.this);
        progressDialog.setCancelable(false);
        objConexion = new ConexionSQLite(UserMain.this, NOMBRE_BASE_DATOS, null, 1);
        showData();

        SearchHospital = findViewById(R.id.btnSearchUserHospital);
        SearchHospital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(UserMain.this, "Search Item", Toast.LENGTH_LONG).show();
            }
        });


    }



    private void showData() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Cursor res = objConexion.getShowDataHospital();
        listHospital.clear();
        while (res.moveToNext()){
            DataHospital hospital = new DataHospital(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3)
            );
            listHospital.add(hospital);
        }
        adapterHospital = new AdapterHospital(UserMain.this, listHospital);
        recycleViewHospital.setAdapter(adapterHospital);
        progressDialog.dismiss();
    }

    public void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void openDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserMain.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you Sure to Logout");
        builder.setPositiveButton("Yes, Logout", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logoutUser();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UserMain.this, "No Logout", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMain.this.finish();
    }

}