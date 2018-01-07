package com.rahbod.visit365.models;

/**
 * Created by Behnam on 1/7/2018.
 */

public class VisList {

    String Clinic;
    String Doctor;
    String CreateDate;
    String Date;
    String VisitDate;
    String TrackingCode;
    String Status;

    public VisList(String clinic, String doctor, String createDate, String date, String visitDate, String trackingCode, String status) {
        Clinic = clinic;
        Doctor = doctor;
        CreateDate = createDate;
        Date = date;
        VisitDate = visitDate;
        TrackingCode = trackingCode;
        Status = status;
    }

    public String getClinic() {
        return Clinic;
    }

    public void setClinic(String clinic) {
        Clinic = clinic;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getVisitDate() {
        return VisitDate;
    }

    public void setVisitDate(String visitDate) {
        VisitDate = visitDate;
    }

    public String getTrackingCode() {
        return TrackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        TrackingCode = trackingCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
