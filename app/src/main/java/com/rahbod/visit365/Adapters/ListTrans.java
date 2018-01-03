package com.rahbod.visit365.Adapters;

/**
 * Created by Behnam on 1/4/2018.
 */

public class ListTrans {

    private String amount;
    private String date;
    private String gateway;
    private String code;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ListTrans(String amount, String date, String gateway, String code) {

        this.amount = amount;
        this.date = date;
        this.gateway = gateway;
        this.code = code;
    }
}
