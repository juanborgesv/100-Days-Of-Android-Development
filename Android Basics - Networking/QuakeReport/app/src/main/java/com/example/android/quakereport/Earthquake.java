package com.example.android.quakereport;

public class Earthquake {

    private String magnitude;
    private String location;
    private String date;

    public Earthquake(String myMagnitude, String myLocation, String myDate)
    {
        magnitude = myMagnitude;
        location = myLocation;
        date = myDate;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
