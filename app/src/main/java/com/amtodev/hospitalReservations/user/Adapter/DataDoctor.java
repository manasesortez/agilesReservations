package com.amtodev.hospitalReservations.user.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class DataDoctor implements Parcelable {

    private Integer doctor_id;
    private String doctor_name;
    private String doctor_direccion;
    private String doctor_costo_consulta;
    private String doctor_dias_habiles;
    private String doctor_hora_entrada;
    private String doctor_hora_salida;
    private String hospital_name;
    private String especialidad_name;

    public DataDoctor(Integer doctor_id, String doctor_name, String doctor_direccion, String doctor_costo_consulta, String doctor_dias_habiles, String doctor_hora_entrada, String doctor_hora_salida, String hospital_name, String especialidad_name) {
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.doctor_direccion = doctor_direccion;
        this.doctor_costo_consulta = doctor_costo_consulta;
        this.doctor_dias_habiles = doctor_dias_habiles;
        this.doctor_hora_entrada = doctor_hora_entrada;
        this.doctor_hora_salida = doctor_hora_salida;
        this.hospital_name = hospital_name;
        this.especialidad_name = especialidad_name;
    }

    public DataDoctor(){

    }

    protected DataDoctor(Parcel in) {
        if (in.readByte() == 0) {
            doctor_id = null;
        } else {
            doctor_id = in.readInt();
        }
        doctor_name = in.readString();
        doctor_direccion = in.readString();
        doctor_costo_consulta = in.readString();
        doctor_dias_habiles = in.readString();
        doctor_hora_entrada = in.readString();
        doctor_hora_salida = in.readString();
        hospital_name = in.readString();
        especialidad_name = in.readString();
    }

    public static final Creator<DataDoctor> CREATOR = new Creator<DataDoctor>() {
        @Override
        public DataDoctor createFromParcel(Parcel in) {
            return new DataDoctor(in);
        }

        @Override
        public DataDoctor[] newArray(int size) {
            return new DataDoctor[size];
        }
    };

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_direccion() {
        return doctor_direccion;
    }

    public void setDoctor_direccion(String doctor_direccion) {
        this.doctor_direccion = doctor_direccion;
    }

    public String getDoctor_costo_consulta() {
        return doctor_costo_consulta;
    }

    public void setDoctor_costo_consulta(String doctor_costo_consulta) {
        this.doctor_costo_consulta = doctor_costo_consulta;
    }

    public String getDoctor_dias_habiles() {
        return doctor_dias_habiles;
    }

    public void setDoctor_dias_habiles(String doctor_dias_habiles) {
        this.doctor_dias_habiles = doctor_dias_habiles;
    }

    public String getDoctor_hora_entrada() {
        return doctor_hora_entrada;
    }

    public void setDoctor_hora_entrada(String doctor_hora_entrada) {
        this.doctor_hora_entrada = doctor_hora_entrada;
    }

    public String getDoctor_hora_salida() {
        return doctor_hora_salida;
    }

    public void setDoctor_hora_salida(String doctor_hora_salida) {
        this.doctor_hora_salida = doctor_hora_salida;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getEspecialidad_name() {
        return especialidad_name;
    }

    public void setEspecialidad_name(String especialidad_name) {
        this.especialidad_name = especialidad_name;
    }

    @Override
    public String toString() {
        return "DataDoctor{" +
                "doctor_id=" + doctor_id +
                ", doctor_name='" + doctor_name + '\'' +
                ", doctor_direccion='" + doctor_direccion + '\'' +
                ", doctor_costo_consulta='" + doctor_costo_consulta + '\'' +
                ", doctor_dias_habiles='" + doctor_dias_habiles + '\'' +
                ", doctor_hora_entrada='" + doctor_hora_entrada + '\'' +
                ", doctor_hora_salida='" + doctor_hora_salida + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", especialidad_name='" + especialidad_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (doctor_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(doctor_id);
        }
        parcel.writeString(doctor_name);
        parcel.writeString(doctor_direccion);
        parcel.writeString(doctor_costo_consulta);
        parcel.writeString(doctor_dias_habiles);
        parcel.writeString(doctor_hora_entrada);
        parcel.writeString(doctor_hora_salida);
        parcel.writeString(hospital_name);
        parcel.writeString(especialidad_name);
    }
}
