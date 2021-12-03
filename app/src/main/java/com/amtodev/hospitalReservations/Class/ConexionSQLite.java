package com.amtodev.hospitalReservations.Class;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amtodev.hospitalReservations.admin.Doctor.ViewDoctor;

public class ConexionSQLite extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "agilesReservas";

    final String TABLE_HOSPITAL = "CREATE TABLE hospitales (" +
            "hospital_id INTEGER PRIMARY KEY  AUTOINCREMENT  UNIQUE NOT NULL," +
            "hospital_nombre TEXT," +
            "hospital_telefono TEXT," +
            "hospital_direccion TEXT)";

    final String TABLE_ESPECIALIDAD = "CREATE TABLE especialidades(" +
            "especialidad_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
            "especialidad_nombre TEXT, " +
            "hospital_id INTEGER NOT NULL," +
            "FOREIGN KEY (hospital_id) REFERENCES hospitales(hospital_id) ON UPDATE CASCADE ON DELETE CASCADE)";

    final String TABLE_DOCTOR= "CREATE TABLE doctores(" +
            "doctor_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
            "doctor_name TEXT, " +
            "doctor_direccion TEXT, " +
            "doctor_costo_consulta TEXT," +
            "doctor_dias_habiles TEXT," +
            "doctor_hora_entrada TEXT," +
            "doctor_hora_salida TEXT," +
            "hospital_id INTEGER NOT NULL," +
            "especialidad_id INTEGER NOT NULL," +
            "FOREIGN KEY (especialidad_id) REFERENCES especialidades(especialidad_id)," +
            "FOREIGN KEY (hospital_id) REFERENCES hospitales(hospital_id) ON UPDATE CASCADE ON DELETE CASCADE )";

    final String TABLE_RESERVAS = "CREATE TABLE reservas(" +
            "reserva_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "reserva_hora_consulta TEXT, " +
            "reserva_nombre_paciente TEXT, " +
            "reserva_estado_paciente TEXT, " +
            "hospital_id INTEGER NOT NULL," +
            "especialidad_id INTEGER NOT NULL," +
            "doctor_id INTEGER NOT NULL," +
            "FOREIGN KEY (hospital_id) REFERENCES hospitales(hospital_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            "FOREIGN KEY (especialidad_id) REFERENCES especialidades(especialidad_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            "FOREIGN KEY (doctor_id) REFERENCES doctores(doctor_id) ON UPDATE CASCADE ON DELETE CASCADE )";

    public ConexionSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(TABLE_HOSPITAL);
        db.execSQL(TABLE_ESPECIALIDAD);
        db.execSQL(TABLE_DOCTOR);
        db.execSQL(TABLE_RESERVAS);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS hospitales");
        db.execSQL("DROP TABLE IF EXISTS especialidades");
        db.execSQL("DROP TABLE IF EXISTS doctores");
        db.execSQL("DROP TABLE IF EXISTS reservas");

        onCreate(db);
    }

    public Cursor getShowDataHospital(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM hospitales ", null);
    }

    public Cursor getShowDataSpecialty(int hospital_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT especialidad_id, especialidad_nombre, hospital_nombre, especialidades.hospital_id FROM especialidades, hospitales WHERE especialidades.hospital_id LIKE  '%"+ hospital_id + "%' AND hospitales.hospital_id LIKE  '%"+ hospital_id + "%' ORDER BY especialidades.especialidad_nombre ASC ", null);
    }

    public Cursor getShowDataDoctor(int especialidad_id, int hospital_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT doctor_id, doctor_name, doctor_direccion, doctor_costo_consulta, doctor_dias_habiles, doctor_hora_entrada, doctor_hora_salida, hospital_nombre, especialidad_nombre, doctores.hospital_id, doctores.especialidad_id FROM doctores, especialidades, hospitales WHERE doctores.especialidad_id LIKE  '%"+ especialidad_id + "%' AND especialidades.especialidad_id LIKE   '%"+ especialidad_id + "%' AND  especialidades.hospital_id LIKE   '%"+ hospital_id + "%' AND  hospitales.hospital_id LIKE   '%"+ hospital_id + "%' ORDER BY doctores.doctor_name DESC ", null);
    }

}
