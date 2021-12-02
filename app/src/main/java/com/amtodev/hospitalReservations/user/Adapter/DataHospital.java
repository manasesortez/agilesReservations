package com.amtodev.hospitalReservations.user.Adapter;

import android.view.View;

public class DataHospital {
    private String hospital_nombre;
    private String hospital_telefono;
    private String hospital_direccion;

    public DataHospital(){

    }

    public DataHospital(String hospital_nombre, String hospital_telefono, String hospital_direccion){
        this.hospital_nombre = hospital_nombre;
        this.hospital_telefono = hospital_telefono;
        this.hospital_direccion = hospital_direccion;
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

}
