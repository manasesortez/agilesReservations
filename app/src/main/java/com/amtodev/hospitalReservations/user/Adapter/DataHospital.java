package com.amtodev.hospitalReservations.user.Adapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class DataHospital implements Parcelable {

    private Integer hospital_id;
    private String hospital_nombre;
    private String hospital_telefono;
    private String hospital_direccion;

    @Override
    public String toString() {
        return "DataHospital{" +
                "hospital_id=" + hospital_id +
                ", hospital_nombre='" + hospital_nombre + '\'' +
                ", hospital_telefono='" + hospital_telefono + '\'' +
                ", hospital_direccion='" + hospital_direccion + '\'' +
                "}";
    }

    public DataHospital(){

    }

    public DataHospital(Integer hospital_id, String hospital_nombre, String hospital_telefono, String hospital_direccion){
        this.hospital_id = hospital_id;
        this.hospital_nombre = hospital_nombre;
        this.hospital_telefono = hospital_telefono;
        this.hospital_direccion = hospital_direccion;
    }

    protected DataHospital(Parcel in) {
        hospital_id = in.readInt();
        hospital_nombre = in.readString();
        hospital_telefono = in.readString();
        hospital_direccion = in.readString();

    }

    public static final Creator<DataHospital> CREATOR = new Creator<DataHospital>() {
        @Override
        public DataHospital createFromParcel(Parcel in) {
            return new DataHospital(in);
        }

        @Override
        public DataHospital[] newArray(int size) {
            return new DataHospital[size];
        }
    };

    public Integer getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(Integer hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospital_nombre() {
        return hospital_nombre;
    }

    public void setHospital_nombre(String hospital_nombre) {
        this.hospital_nombre = hospital_nombre;
    }

    public String getHospital_telefono() {
        return hospital_telefono;
    }

    public void setHospital_telefono(String hospital_telefono) {
        this.hospital_telefono = hospital_telefono;
    }

    public String getHospital_direccion() {
        return hospital_direccion;
    }

    public void setHospital_direccion(String hospital_direccion) {
        this.hospital_direccion = hospital_direccion;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hospital_id);
        parcel.writeString(hospital_nombre);
        parcel.writeString(hospital_telefono);
        parcel.writeString(hospital_direccion);
    }
}
