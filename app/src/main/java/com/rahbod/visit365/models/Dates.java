package com.rahbod.visit365.models;

/**
 * Created by moien on 02/01/2018.
 */

public class Dates {
    private String date;
    private String am;
    private String pm;
    private String dateShow;

    public Dates(String date, String dateShow) {
        this.date = date;
        this.dateShow = dateShow;
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

    public String getDateShow() {
        return dateShow;
    }

    public void setDateShow(String dateShow) {
        this.dateShow = dateShow;
    }
}
