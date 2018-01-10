package com.rahbod.visit365.models;

public class DrList {
    private String doctorName;
    private String reserveDay;
    private String avatar;
    private String clinicName;
    private long clinicPhone;
    private int clinicId;
    private int doctorId;

    public DrList(String name, String avatar, int doctorId, int clinicId, String reserveDay) {
        this.doctorName = name;
        this.avatar = avatar;
        this.clinicId = clinicId;
        this.doctorId = doctorId;
        this.reserveDay = reserveDay;
    }

    public DrList(String doctorName, String avatar) {
        this.doctorName = doctorName;
        this.avatar = avatar;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public long getClinicPhone() {
        return clinicPhone;
    }

    public void setClinicPhone(long clinicPhone) {
        this.clinicPhone = clinicPhone;
    }

    public DrList(String clinicName, long clinicPhone) {
        this.clinicName = clinicName;
        this.clinicPhone = clinicPhone;

    }

    public String getReserveDay() {
        return reserveDay;
    }

    public void setReserveDay(String reserveDay) {
        this.reserveDay = reserveDay;
    }

    public String getName() {
        return doctorName;
    }

    public void setName(String name) {
        this.doctorName = name;
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