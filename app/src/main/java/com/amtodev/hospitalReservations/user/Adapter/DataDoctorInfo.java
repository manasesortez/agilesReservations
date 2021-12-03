package com.amtodev.hospitalReservations.user.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class DataDoctorInfo implements Parcelable {

    private Integer hospital_id;
    private Integer especialidad_id;
    private Integer doctor_id;
    private String hospital_name;
    private String especialidad_name;
    private String doctor_name;

    public DataDoctorInfo(Integer hospital_id, Integer especialidad_id, Integer doctor_id, String hospital_name, String especialidad_name, String doctor_name) {
        this.hospital_id = hospital_id;
        this.especialidad_id = especialidad_id;
        this.doctor_id = doctor_id;
        this.hospital_name = hospital_name;
        this.especialidad_name = especialidad_name;
        this.doctor_name = doctor_name;
    }


    protected DataDoctorInfo(Parcel in) {
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
        hospital_name = in.readString();
        especialidad_name = in.readString();
        doctor_name = in.readString();
    }

    public static final Creator<DataDoctorInfo> CREATOR = new Creator<DataDoctorInfo>() {
        @Override
        public DataDoctorInfo createFromParcel(Parcel in) {
            return new DataDoctorInfo(in);
        }

        @Override
        public DataDoctorInfo[] newArray(int size) {
            return new DataDoctorInfo[size];
        }
    };

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

    @Override
    public String toString() {
        return "DataDoctorInfo{" +
                "hospital_id=" + hospital_id +
                ", especialidad_id=" + especialidad_id +
                ", doctor_id=" + doctor_id +
                ", hospital_name='" + hospital_name + '\'' +
                ", especialidad_name='" + especialidad_name + '\'' +
                ", doctor_name='" + doctor_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
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
        parcel.writeString(hospital_name);
        parcel.writeString(especialidad_name);
        parcel.writeString(doctor_name);
    }
}
