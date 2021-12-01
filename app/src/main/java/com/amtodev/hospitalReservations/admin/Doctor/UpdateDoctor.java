package com.amtodev.hospitalReservations.admin.Doctor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Specialty.ViewSpecialty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateDoctor extends AppCompatActivity {

    TextView tv_horaEntradaUpdate, tv_horaSalidaUpdate;
    int t1Hora, t1Minute, t2Hora, t2Minute;
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DE_DATOS = "agilesReservas";
    EditText DoctorNameUpdate, DoctorAddressUpdate, DoctorPhoneUpdate, BussinessDayUpdate;
    Spinner DoctorHospitalUpdate, DoctorSpecialtyUpdate;
    Button UpdateDoctor, DeleteDoctor;
    private Cursor fila;
    int doctores_id;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);
        objConexion = new ConexionSQLite(UpdateDoctor.this, NOMBRE_BASE_DE_DATOS, null, 1);
        db = objConexion.getWritableDatabase();

        tv_horaEntradaUpdate = (TextView) findViewById(R.id.tv_horaEntradaDoctorUpdate);
        tv_horaSalidaUpdate = (TextView) findViewById(R.id.tv_horaSalidaDoctorUpdate);

        DoctorNameUpdate = findViewById(R.id.txtNombreDoctorUpdate);
        DoctorAddressUpdate = findViewById(R.id.txtAddressDoctorUpdate);
        DoctorPhoneUpdate = findViewById(R.id.txtCostoDoctorUpdate);
        BussinessDayUpdate = findViewById(R.id.txtBussinessDayDoctorUpdate);

        DoctorHospitalUpdate = (Spinner) findViewById(R.id.lvHospitalDoctorUpdate);
        DoctorSpecialtyUpdate = (Spinner) findViewById(R.id.lvSpecialtyDoctorUpdate);

        UpdateDoctor = (Button) findViewById(R.id.btnSaveDoctorUpdate);
        DeleteDoctor = (Button) findViewById(R.id.btnDeleteDoctor);

        tv_horaEntradaUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        UpdateDoctor.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int HourOfDay, int minute) {
                                t1Hora = HourOfDay;
                                t1Minute = minute;

                                String time = t1Hora + ":" + t1Minute;
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);

                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm:aa"
                                    );
                                    tv_horaEntradaUpdate.setText(f12Hours.format(date));
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1Hora, t1Minute);
                timePickerDialog.show();
            }
        });
        tv_horaSalidaUpdate.setOnClickListener(new View.OnClickListener()     {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        UpdateDoctor.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int HourOfDay, int minute) {
                                t2Hora = HourOfDay;
                                t2Minute = minute;

                                String time = t2Hora + ":" + t2Minute;
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);

                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm:aa"
                                    );
                                    tv_horaSalidaUpdate.setText(f12Hours.format(date));
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2Hora, t2Minute);
                timePickerDialog.show();
            }
        });
        spinnerHospital();
        spinnerSpecialty();


        UpdateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DoctorNameUpdate.getText().toString().isEmpty()){
                    Toast.makeText(UpdateDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }if (DoctorAddressUpdate.getText().toString().isEmpty()){
                    Toast.makeText(UpdateDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }if (DoctorPhoneUpdate.getText().toString().isEmpty()){
                    Toast.makeText(UpdateDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }if (BussinessDayUpdate.getText().toString().isEmpty()){
                    Toast.makeText(UpdateDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }else {
                    Update();
                    DoctorNameUpdate.getText().clear();
                    DoctorAddressUpdate.getText().clear();
                    DoctorPhoneUpdate.getText().clear();
                    BussinessDayUpdate.getText().clear();

                }
            }
        });
        DeleteDoctor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openUpdateDoctor(View view) {
        startActivity(new Intent(this, ViewDoctor.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    private void spinnerSpecialty() {
        try {
            fila = db.rawQuery("SELECT especialidad_id AS _id ,especialidad_nombre, hospital_nombre FROM especialidades, hospitales WHERE especialidades.hospital_id = hospitales.hospital_id  ORDER BY especialidad_nombre ASC", null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, fila,
                    new String[] {"especialidad_nombre"}, new int[] {android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            DoctorSpecialtyUpdate.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(this, "Error:  " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void spinnerHospital() {
        try {
            fila = db.rawQuery("SELECT hospital_id AS _id, hospital_nombre FROM hospitales ORDER BY hospital_nombre", null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, fila,
                    new String[] {"hospital_nombre"}, new int[] {android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            DoctorHospitalUpdate.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(this, "Error:  " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void GoBackActivity(){
        Intent actividad = new Intent(UpdateDoctor.this, ViewSpecialty.class);
        startActivity(actividad);
        UpdateDoctor.this.finish();
    }

    private void Update(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "UPDATE doctores SET doctor_name='"+ DoctorNameUpdate.getText() + "'," + "doctor_direccion='"+ DoctorAddressUpdate.getText() + "'," + "doctor_costo_consulta='"+ DoctorPhoneUpdate.getText() + "'," + "doctor_dias_habiles='"+ BussinessDayUpdate.getText() + "'," + "doctor_hora_entrada='"+ tv_horaEntradaUpdate.getText() + "'," + "doctor_hora_salida='"+ tv_horaSalidaUpdate.getText() + "'," + "hospital_id='"+ DoctorHospitalUpdate.getSelectedItemId() + "'," + "especialidad_id='"+ DoctorSpecialtyUpdate.getSelectedItemId() + "' WHERE doctor_id = '" + doctores_id + "'";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(UpdateDoctor.this, "Specialty Successfully Update", Toast.LENGTH_SHORT).show();
        }catch (Exception error){
            Toast.makeText(UpdateDoctor.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales == null){
            Toast.makeText(UpdateDoctor.this, "You need send some Hospital ID", Toast.LENGTH_SHORT).show();
            doctores_id = 0;
            GoBackActivity();
        }else{
            doctores_id = valoresAdicionales.getInt("doctor_id");
            verContacto();
        }
    }

    private void verContacto(){
        try{
            SQLiteDatabase base = objConexion.getReadableDatabase();
            String consulta = "SELECT doctor_id,doctor_name, doctor_direccion, doctor_costo_consulta, doctor_dias_habiles, doctor_hora_entrada, doctor_hora_salida FROM doctores WHERE doctor_id = '"+ doctores_id +"'";
            @SuppressLint("Recycle") Cursor cadaRegistro = base.rawQuery(consulta, null);
            if (cadaRegistro.moveToFirst()){
                do{
                    DoctorNameUpdate.setText(cadaRegistro.getString(1));
                    DoctorAddressUpdate.setText(cadaRegistro.getString(2));
                    DoctorPhoneUpdate.setText(cadaRegistro.getString(3));
                    BussinessDayUpdate.setText(cadaRegistro.getString(4));
                    tv_horaEntradaUpdate.setText(cadaRegistro.getString(5));
                    tv_horaSalidaUpdate.setText(cadaRegistro.getString(6));
                }while(cadaRegistro.moveToNext());
            }
        }catch (Exception error){
            Toast.makeText(UpdateDoctor.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    private void delete(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "DELETE FROM doctores WHERE doctor_id ='"+ doctores_id + "'";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(UpdateDoctor.this, "Hospital successfully Delete", Toast.LENGTH_SHORT).show();
        }catch (Exception error){
            Toast.makeText(UpdateDoctor.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDoctor.this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to delete this Hospital?");
        builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateDoctor.this, "Hospital no Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        DoctorNameUpdate.getText().clear();
        DoctorAddressUpdate.getText().clear();
        DoctorPhoneUpdate.getText().clear();
        BussinessDayUpdate.getText().clear();
        builder.create();
        builder.show();
    }
}