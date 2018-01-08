package com.rahbod.visit365.models;

/**
 * Created by Behnam on 1/7/2018.
 */

public class VisList {

    String strClinic;
    String strDoctor;
    String strCreateDate;
    String strDate;
    String strVisitDate;
    String strTrackingCode;
    String strStatus;

    public VisList(String strClinic, String strDoctor, String strCreateDate, String strDate, String strVisitDate, String strTrackingCode, String strStatus) {
        this.strClinic = strClinic;
        this.strDoctor = strDoctor;
        this.strCreateDate = strCreateDate;
        this.strDate = strDate;
        this.strVisitDate = strVisitDate;
        this.strTrackingCode = strTrackingCode;
        this.strStatus = strStatus;
    }

    public String getStrClinic() {
        return strClinic;
    }

    public void setStrClinic(String strClinic) {
        this.strClinic = strClinic;
    }

    public String getStrDoctor() {
        return strDoctor;
    }

    public void setStrDoctor(String strDoctor) {
        this.strDoctor = strDoctor;
    }

    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrVisitDate() {
        return strVisitDate;
    }

    public void setStrVisitDate(String strVisitDate) {
        this.strVisitDate = strVisitDate;
    }

    public String getStrTrackingCode() {
        return strTrackingCode;
    }

    public void setStrTrackingCode(String strTrackingCode) {
        this.strTrackingCode = strTrackingCode;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }
}

