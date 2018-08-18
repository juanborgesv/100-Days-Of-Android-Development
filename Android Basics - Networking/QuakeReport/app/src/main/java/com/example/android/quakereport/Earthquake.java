package com.example.android.quakereport;

public class Earthquake {

    private double magnitude;
    private String location;
    private String date;
    private String url;
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

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param myMagnitude is the magnitude (size) of the earthquake
     * @param myLocation is the location where the earthquake happened
     * @param myTime is the time in milliseconds (from the Epoch) when the earthquake happened
     * @param myUrl is the website URL to find more details about the earthquake
     */
    public Earthquake(double myMagnitude, String myLocation, long myTime, String myUrl)
    {
        magnitude = myMagnitude;
        location = myLocation;
        timeInMiliseconds = myTime;
        url = myUrl;
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

    public String getUrl() {
        return url;
    }
}
