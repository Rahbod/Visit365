package com.rahbod.visit365.models;

public class DateTime {
    private String am;
    private String pm;
    private String dateShow;

    public DateTime(String date, String dateShow, String am, String pm) {
        this.date = date;
        this.dateShow = dateShow;
        this.pm = pm;
        this.am = am;
    }

    public DateTime(String am, String pm) {
        this.am = am;
        this.pm = pm;
    }


    public DateTime(String pm) {
        this.pm = pm;
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
