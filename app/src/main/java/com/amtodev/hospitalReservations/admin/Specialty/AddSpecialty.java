package com.amtodev.hospitalReservations.admin.Specialty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.admin.Doctor.AdminDoctor;
import com.amtodev.hospitalReservations.admin.Hospital.AddHospital;

public class AddSpecialty extends AppCompatActivity {

    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DE_DATOS = "agilesReservas";
    EditText SpecialtyName;
    Spinner spinnerHospital;
    Button btn_AddSpecialty;
    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specialty);
        objConexion = new ConexionSQLite(AddSpecialty.this, NOMBRE_BASE_DE_DATOS, null, 1);
        db = objConexion.getWritableDatabase();

        SpecialtyName = (EditText) findViewById(R.id.txtSpecialtyName);
        spinnerHospital  = (Spinner) findViewById(R.id.spinnerHospital);
        btn_AddSpecialty  = (Button) findViewById(R.id.btnSaveSpecialty);
        spinner();
        btn_AddSpecialty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (SpecialtyName.getText().toString().isEmpty()){
                    Toast.makeText(AddSpecialty.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }else {
                    registrar();
                    SpecialtyName.getText().clear();

                }
            }
        });

    }

    public void spinner(){
        try {
            fila = db.rawQuery("SELECT hospital_id AS _id, hospital_nombre FROM hospitales ORDER BY hospital_nombre", null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, fila,
                    new String[] {"hospital_nombre"}, new int[] {android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHospital.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(this, "Error:  " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void openAdminSpecialty(View view) {
        startActivity(new Intent(this, AdminSpecialty.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    private void registrar(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            values = new ContentValues();

            values.put("especialidad_nombre", SpecialtyName.getText().toString());
            values.put("hospital_id", spinnerHospital.getSelectedItemId());
            miBaseDatos.insert("especialidades", null, values);
            miBaseDatos.close();
            Toast.makeText(AddSpecialty.this, SpecialtyName.getText().toString() +" Register Created Successfully", Toast.LENGTH_LONG).show();
        }catch(Exception error){
            Toast.makeText(AddSpecialty.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}