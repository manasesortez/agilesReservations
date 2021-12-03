package com.amtodev.hospitalReservations.user.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class DataReservas implements Parcelable {

    private Integer reserva_id;
    private String reserva_nombre_paciente;
    private String reserva_estado_paciente;
    private String hospital_name;
    private String especialidad_name;
    private String doctor_name;
    private Integer hospital_id;
    private Integer especialidad_id;
    private Integer doctor_id;

    protected DataReservas(Parcel in) {
        if (in.readByte() == 0) {
            reserva_id = null;
        } else {
            reserva_id = in.readInt();
        }
        reserva_nombre_paciente = in.readString();
        reserva_estado_paciente = in.readString();
        hospital_name = in.readString();
        especialidad_name = in.readString();
        doctor_name = in.readString();
        if (in.readByte() == 0) {
            hospital_id = null;
        } else {
            hospital_id = in.readInt();
        }
        if (in.readByte() == 0) {
            especialidad_id = null;
        } else {
            especialidad_id = in.readInt();
        }
        if (in.readByte() == 0) {
            doctor_id = null;
        } else {
            doctor_id = in.readInt();
        }
    }

    public static final Creator<DataReservas> CREATOR = new Creator<DataReservas>() {
        @Override
        public DataReservas createFromParcel(Parcel in) {
            return new DataReservas(in);
        }

        @Override
        public DataReservas[] newArray(int size) {
            return new DataReservas[size];
        }
    };

    public Integer getReserva_id() {
        return reserva_id;
    }

    public void setReserva_id(Integer reserva_id) {
        this.reserva_id = reserva_id;
    }

    public String getReserva_nombre_paciente() {
        return reserva_nombre_paciente;
    }

    public void setReserva_nombre_paciente(String reserva_nombre_paciente) {
        this.reserva_nombre_paciente = reserva_nombre_paciente;
    }

    public String getReserva_estado_paciente() {
        return reserva_estado_paciente;
    }

    public void setReserva_estado_paciente(String reserva_estado_paciente) {
        this.reserva_estado_paciente = reserva_estado_paciente;
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

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public Integer getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(Integer hospital_id) {
        this.hospital_id = hospital_id;
    }

    public Integer getEspecialidad_id() {
        return especialidad_id;
    }

    public void setEspecialidad_id(Integer especialidad_id) {
        this.especialidad_id = especialidad_id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public DataReservas(Integer reserva_id, String reserva_nombre_paciente, String reserva_estado_paciente, String hospital_name, String especialidad_name, String doctor_name, Integer hospital_id, Integer especialidad_id, Integer doctor_id) {
        this.reserva_id = reserva_id;
        this.reserva_nombre_paciente = reserva_nombre_paciente;
        this.reserva_estado_paciente = reserva_estado_paciente;
        this.hospital_name = hospital_name;
        this.especialidad_name = especialidad_name;
        this.doctor_name = doctor_name;
        this.hospital_id = hospital_id;
        this.especialidad_id = especialidad_id;
        this.doctor_id = doctor_id;
    }

    @Override
    public String toString() {
        return "DataReservas{" +
                "reserva_id=" + reserva_id +
                ", reserva_nombre_paciente='" + reserva_nombre_paciente + '\'' +
                ", reserva_estado_paciente='" + reserva_estado_paciente + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", especialidad_name='" + especialidad_name + '\'' +
                ", doctor_name='" + doctor_name + '\'' +
                ", hospital_id=" + hospital_id +
                ", especialidad_id=" + especialidad_id +
                ", doctor_id=" + doctor_id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (reserva_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(reserva_id);
        }
        parcel.writeString(reserva_nombre_paciente);
        parcel.writeString(reserva_estado_paciente);
        parcel.writeString(hospital_name);
        parcel.writeString(especialidad_name);
        parcel.writeString(doctor_name);
        if (hospital_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(hospital_id);
        }
        if (especialidad_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(especialidad_id);
        }
        if (doctor_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(doctor_id);
        }
    }
}
