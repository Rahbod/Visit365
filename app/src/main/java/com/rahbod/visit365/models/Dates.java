package com.rahbod.visit365.models;

/**
 * Created by moien on 02/01/2018.
 */

public class Dates {
    private String date;

    public Dates(String am, String pm) {
        this.am = am;
        this.pm = pm;
    }

    private String am;
    private String pm;

    public Dates(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }
}
