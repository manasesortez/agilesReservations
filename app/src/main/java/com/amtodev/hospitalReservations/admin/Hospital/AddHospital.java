package com.amtodev.hospitalReservations.admin.Hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;

public class AddHospital extends AppCompatActivity {

    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DE_DATOS = "agilesReservas";
    EditText HospitalName, NumberPhone, Address;
    Button SaveHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);
        objConexion = new ConexionSQLite(AddHospital.this, NOMBRE_BASE_DE_DATOS, null, 1);

        HospitalName = (EditText) findViewById(R.id.txtHospitalName);
        NumberPhone = (EditText) findViewById(R.id.txtNumberPhone);
        Address = (EditText) findViewById(R.id.txtAddress);

        SaveHospital = (Button) findViewById(R.id.btnSaveHospital);
        SaveHospital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (HospitalName.getText().toString().isEmpty()){
                    Toast.makeText(AddHospital.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }else if (NumberPhone.getText().toString().isEmpty()){
                    Toast.makeText(AddHospital.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }else if (Address.getText().toString().isEmpty()){
                    Toast.makeText(AddHospital.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }else {
                    registrar();
                    HospitalName.getText().clear();
                    NumberPhone.getText().clear();
                    Address.getText().clear();

                }
            }
        });

    }

    public void openAdminHospital(View view) {
        startActivity(new Intent(this, Admin.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    private void registrar(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "INSERT INTO hospitales (hospital_nombre, hospital_telefono, hospital_direccion) VALUES" +
                    "('" + HospitalName.getText() + "','"+ NumberPhone.getText() + "','"+ Address.getText() + "')";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(AddHospital.this, HospitalName.getText().toString() +" registrado con exito", Toast.LENGTH_LONG).show();
        }catch(Exception error){
            Toast.makeText(AddHospital.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}