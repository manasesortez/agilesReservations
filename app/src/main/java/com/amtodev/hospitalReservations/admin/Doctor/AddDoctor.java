package com.amtodev.hospitalReservations.admin.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.admin.Specialty.AddSpecialty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDoctor extends AppCompatActivity {

    TextView tv_horaEntrada, tv_horaSalida;
    int t1Hora, t1Minute, t2Hora, t2Minute;
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DE_DATOS = "agilesReservas";
    EditText DoctorName, DoctorAddress, DoctorPhone, BussinessDay;
    Spinner DoctorHospital, DoctorSpecialty;
    Button btn_AddDoctor;
    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        objConexion = new ConexionSQLite(AddDoctor.this, NOMBRE_BASE_DE_DATOS, null, 1);
        db = objConexion.getWritableDatabase();

        tv_horaEntrada = findViewById(R.id.tv_horaEntrada);
        tv_horaSalida = findViewById(R.id.tv_horaSalida);

        DoctorName = findViewById(R.id.txtDoctorName);
        DoctorAddress = findViewById(R.id.txtDoctorAddress);
        DoctorPhone = findViewById(R.id.txtDoctorPhone);
        BussinessDay = findViewById(R.id.txtBussinessDay);


        DoctorHospital = findViewById(R.id.spinnerDoctorHospital);
        DoctorSpecialty = findViewById(R.id.spinnerDoctorSpecialty);
        spinnerHospital();
        spinnerSpecialty();

        tv_horaEntrada.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddDoctor.this,
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
                                    tv_horaEntrada.setText(f12Hours.format(date));
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
        tv_horaSalida.setOnClickListener(new View.OnClickListener()     {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddDoctor.this,
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
                                    tv_horaSalida.setText(f12Hours.format(date));
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

        btn_AddDoctor = (Button) findViewById(R.id.btnSaveDoctor);
        btn_AddDoctor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (DoctorName.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }if (DoctorAddress.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }if (DoctorPhone.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }if (BussinessDay.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }else {
                    registrar();
                    DoctorName.getText().clear();
                    DoctorAddress.getText().clear();
                    DoctorPhone.getText().clear();
                    BussinessDay.getText().clear();

                }
            }
        });
    }

    private void spinnerSpecialty() {
        try {
            fila = db.rawQuery("SELECT especialidad_id AS _id ,especialidad_nombre, hospital_nombre FROM especialidades, hospitales WHERE especialidades.hospital_id = hospitales.hospital_id  ORDER BY especialidad_nombre ASC", null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, fila,
                    new String[] {"especialidad_nombre"}, new int[] {android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            DoctorSpecialty.setAdapter(adapter);
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
            DoctorHospital.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(this, "Error:  " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void openAdminDoctor(View view) {
        startActivity(new Intent(this, AdminDoctor.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    private void registrar(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            values = new ContentValues();

            values.put("doctor_name", DoctorName.getText().toString());
            values.put("doctor_direccion", DoctorAddress.getText().toString());
            values.put("doctor_costo_consulta", DoctorPhone.getText().toString());
            values.put("doctor_dias_habiles", BussinessDay.getText().toString());
            values.put("doctor_hora_entrada", tv_horaEntrada.getText().toString());
            values.put("doctor_hora_salida", tv_horaSalida.getText().toString());
            values.put("hospital_id", DoctorHospital.getSelectedItemId());
            values.put("especialidad_id", DoctorSpecialty.getSelectedItemId());

            miBaseDatos.insert("doctores", null, values);
            miBaseDatos.close();
            Toast.makeText(AddDoctor.this, DoctorName.getText().toString() +" Register Created Successfully", Toast.LENGTH_LONG).show();
        }catch(Exception error){
            Toast.makeText(AddDoctor.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}