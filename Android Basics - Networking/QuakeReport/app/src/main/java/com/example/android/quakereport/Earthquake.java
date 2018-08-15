package com.example.android.quakereport;

public class Earthquake {

    private double magnitude;
    private String location;
    private String date;
    private long timeInMiliseconds;


    public Earthquake(double myMagnitude, String myLocation, String myDate)
    {
        magnitude = myMagnitude;
        location = myLocation;
        date = myDate;
    }

    public Earthquake(double myMagnitude, String myLocation, long myTime)
    {
        magnitude = myMagnitude;
        location = myLocation;
        timeInMiliseconds = myTime;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public long getTimeInMiliseconds() {
        return timeInMiliseconds;
    }
}
