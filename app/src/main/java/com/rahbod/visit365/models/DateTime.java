package com.rahbod.visit365.models;

import java.util.Objects;

public class DateTime {
    private String text;
    private String time;

    public DateTime(String text, String time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public boolean hasAM() {
        return time == "AM";
    }

    public boolean hasPM() {
        return Objects.equals(time, "PM");
    }
}