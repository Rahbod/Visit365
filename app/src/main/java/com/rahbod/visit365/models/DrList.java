package com.rahbod.visit365.models;

/**
 * Created by moien on 31/12/2017.
 */

public class DrList {
    public String name;
    public String reserveDay;
    public String avatar;
    public int clinicId;
    public int doctorId;

    public DrList() {
    }

    public DrList(String name, String avatar, int doctorId, int clinicId , String reserveDay) {
        this.name = name;
        this.avatar = avatar;
        this.clinicId = clinicId;
        this.doctorId = doctorId;
        this.reserveDay = reserveDay;
    }

    public String getReserveDay() {
        return reserveDay;
    }

    public void setReserveDay(String reserveDay) {
        this.reserveDay = reserveDay;
    }

    public DrList(int clinicId, int doctorId) {
        this.clinicId = clinicId;
        this.doctorId = doctorId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }


}
