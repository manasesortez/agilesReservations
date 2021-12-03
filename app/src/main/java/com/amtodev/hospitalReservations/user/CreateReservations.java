package com.amtodev.hospitalReservations.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.Class.Configurations;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Doctor.AddDoctor;
import com.amtodev.hospitalReservations.user.Adapter.AdapterDoctor;
import com.amtodev.hospitalReservations.user.Adapter.AdapterDoctorInfo;
import com.amtodev.hospitalReservations.user.Adapter.DataDoctor;
import com.amtodev.hospitalReservations.user.Adapter.DataDoctorInfo;
import com.amtodev.hospitalReservations.user.Adapter.DataSpecialty;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateReservations extends AppCompatActivity implements AdapterDoctorInfo.OnDoctorInfoListener{

    private static final String TAG = "CreateReservations";

    TextView tv_horaConsulta;
    ImageView backActivity;
    int t1Hora, t1Minute;
    EditText txtNamePatient, txtStatusPatient;
    Button btnSaveReservation;

    RecyclerView recycleViewDoctorInfo;
    AdapterDoctorInfo adapterDoctorInfo;
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "agilesReservas";
    Configurations objConfiguracion;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    List<Integer> arregloID = new ArrayList<Integer>();
    ArrayList<DataDoctorInfo> listDoctorInfo = new ArrayList<>();
    ProgressDialog progressDialog;

    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservations);

        progressDialog = new ProgressDialog(CreateReservations.this);
        progressDialog.setCancelable(false);
        objConexion = new ConexionSQLite(CreateReservations.this, NOMBRE_BASE_DATOS, null, 1);
        db = objConexion.getWritableDatabase();

        tv_horaConsulta = findViewById(R.id.txtConsultationTime);
        backActivity = findViewById(R.id.backActivityReservations);
        backActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AfterActivity();
            }
        });

        txtNamePatient = findViewById(R.id.txtNamePacient);
        txtStatusPatient = findViewById(R.id.txtStatusPatient);

        btnSaveReservation = findViewById(R.id.btnSaveReservation);
        tv_horaConsulta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CreateReservations.this,
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
                                    tv_horaConsulta.setText(f12Hours.format(date));
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
        recycleViewDoctorInfo = findViewById(R.id.recycleViewDoctorInfo);

        if(getIntent().hasExtra("doctor_id")) {
            DataDoctor doctor = getIntent().getParcelableExtra("doctor_id");
            int doctor_id = doctor.getDoctor_id();
        }

        btnSaveReservation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (txtNamePatient.getText().toString().isEmpty()){
                    Toast.makeText(CreateReservations.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }if (txtStatusPatient.getText().toString().isEmpty()){
                    Toast.makeText(CreateReservations.this, "Don't leave field empty",
                            Toast.LENGTH_LONG).show();
                }else {
                    registrar();
                    txtNamePatient.getText().clear();
                    txtStatusPatient.getText().clear();

                }
            }
        });
        showDataDoctorInfo();
    }

    private void showDataDoctorInfo() {
        try{
            if(getIntent().hasExtra("doctor_id")) {
                DataDoctor DoctorInfo= getIntent().getParcelableExtra("doctor_id");
                int especialidad_id = DoctorInfo.getEspecialidad_id();
                int hospital_id = DoctorInfo.getHospital_id();
                int doctor_id = DoctorInfo.getDoctor_id();

                DataDoctorInfo doctorInfo;
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Cursor res = objConexion.getShowDataDoctorInfo(especialidad_id, hospital_id, doctor_id);
                listDoctorInfo.clear();
                while (res.moveToNext()){
                    doctorInfo = new DataDoctorInfo(
                            res.getInt(0),
                            res.getInt(1),
                            res.getInt(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5)
                    );
                    listDoctorInfo.add(doctorInfo);
                }
                adapterDoctorInfo = new AdapterDoctorInfo(CreateReservations.this, listDoctorInfo, this);
                recycleViewDoctorInfo.setAdapter(adapterDoctorInfo);
                progressDialog.dismiss();
            }
        }catch(Exception error){
            Toast.makeText(CreateReservations.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void registrar(){
        if(getIntent().hasExtra("doctor_id")) {
            try{
                DataDoctor DoctorInfo= getIntent().getParcelableExtra("doctor_id");
                int especialidad_id = DoctorInfo.getEspecialidad_id();
                int hospital_id = DoctorInfo.getHospital_id();
                int doctor_id = DoctorInfo.getDoctor_id();
                fAuth = FirebaseAuth.getInstance();
                String UserId = fAuth.getCurrentUser().getUid();

                Toast.makeText(CreateReservations.this, "Uid: " + UserId, Toast.LENGTH_LONG).show();

                SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
                values = new ContentValues();

                values.put("reserva_hora_consulta", tv_horaConsulta.getText().toString());
                values.put("reserva_nombre_paciente", txtNamePatient.getText().toString());
                values.put("reserva_estado_paciente", txtStatusPatient.getText().toString());
                values.put("hospital_id", hospital_id);
                values.put("especialidad_id", especialidad_id);
                values.put("doctor_id", doctor_id);
                values.put("reservas_user_id", UserId.toString());

                miBaseDatos.insert("reservas", null, values);
                miBaseDatos.close();
                Toast.makeText(CreateReservations.this, "Reservation Created Successfully", Toast.LENGTH_LONG).show();
            }catch(Exception error){
                Toast.makeText(CreateReservations.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void AfterActivity() {
        startActivity(new Intent(getApplicationContext(), UserMain.class));
        finish();
    }

    @Override
    public void onDoctorInfoClick(int position) {

    }
}