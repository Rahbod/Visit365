package com.rahbod.visit365.models;

/**
 * Created by moien on 02/01/2018.
 */

public class Dates {
    private Long unixTime;
    private String date;
    private String am;
    private String pm;
    private String dateShow;

    public Dates(Long unixTime, String date, String dateShow, String am, String pm) {
        this.unixTime = unixTime;
        this.date = date;
        this.dateShow = dateShow;
        this.pm = pm;
        this.am = am;
    }

    public Dates(String am, String pm) {
        this.am = am;
        this.pm = pm;
    }


    public Dates(String pm) {
        this.pm = pm;
    }


    public Long getUnix() {
        return unixTime;
    }

    public String getDate() {
        return date;
    }

    public void setUnix(Long unixTime) {
        this.unixTime = unixTime;
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