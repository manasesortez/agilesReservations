package com.amtodev.hospitalReservations.admin.Hospital;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;

public class UpdateHospital extends AppCompatActivity {

    EditText HospitalNameUp, NumberPhoneUp, AddressUp;
    Button Button_HospitalUpdate, Button_HospitalDelete;
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "agilesReservas";
    int hospital_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hospital);

        objConexion = new ConexionSQLite(UpdateHospital.this, NOMBRE_BASE_DATOS, null, 1);
        HospitalNameUp = (EditText) findViewById(R.id.txtHospitalNameUpdate);
        NumberPhoneUp = (EditText) findViewById(R.id.txtNumberPhoneUpdate);
        AddressUp = (EditText) findViewById(R.id.txtAddressUpdate);

        Button_HospitalUpdate = (Button) findViewById(R.id.btnUpdateHospital);
        Button_HospitalDelete = (Button) findViewById(R.id.btnDeleteHospital);

        Button_HospitalUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (HospitalNameUp.getText().toString().isEmpty()){
                    Toast.makeText(UpdateHospital.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                }else if (NumberPhoneUp.getText().toString().isEmpty()){
                    Toast.makeText(UpdateHospital.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                }else if (AddressUp.getText().toString().isEmpty()){
                    Toast.makeText(UpdateHospital.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                }else {
                    Update();
                    HospitalNameUp.getText().clear();
                    NumberPhoneUp.getText().clear();
                    AddressUp.getText().clear();

                }
            }
        });

        Button_HospitalDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void GoBackActivity(){
        Intent actividad = new Intent(UpdateHospital.this, ViewHospital.class);
        startActivity(actividad);
        UpdateHospital.this.finish();
    }



    private void Update(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "UPDATE hospitales SET hospital_nombre='"+ HospitalNameUp.getText() + "'," + "hospital_telefono='"+ NumberPhoneUp.getText() + "'," + "hospital_direccion='"+ AddressUp.getText() + "' WHERE hospital_id= '" + hospital_id + "'";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(UpdateHospital.this, "InterHospital Successfully Update", Toast.LENGTH_SHORT).show();
        }catch (Exception error){
            Toast.makeText(UpdateHospital.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "DELETE FROM hospitales WHERE hospital_id ='"+ hospital_id + "'";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(UpdateHospital.this, "InterHospital successfully Delete", Toast.LENGTH_SHORT).show();
        }catch (Exception error){
            Toast.makeText(UpdateHospital.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void openUpdateHospital(View view) {
        startActivity(new Intent(this, ViewHospital.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales == null){
            Toast.makeText(UpdateHospital.this, "You need send some InterHospital ID", Toast.LENGTH_SHORT).show();
            hospital_id = 0;
            GoBackActivity();

        }else{
            hospital_id = valoresAdicionales.getInt("hospital_id");
            verContacto();
        }
    }

    private void verContacto(){
        try{
            SQLiteDatabase base = objConexion.getReadableDatabase();
            String consulta = "SELECT hospital_id,hospital_nombre,hospital_telefono,hospital_direccion FROM hospitales WHERE hospital_id ='"+ hospital_id +"'";

            @SuppressLint("Recycle") Cursor cadaRegistro = base.rawQuery(consulta, null);
            if (cadaRegistro.moveToFirst()){
                do{
                    HospitalNameUp.setText(cadaRegistro.getString(1));
                    NumberPhoneUp.setText(cadaRegistro.getString(2));
                    AddressUp.setText(cadaRegistro.getString(3));
                }while(cadaRegistro.moveToNext());
            }
        }catch (Exception error){
            Toast.makeText(UpdateHospital.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateHospital.this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to delete this InterHospital?");
        builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateHospital.this, "InterHospital no Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

}