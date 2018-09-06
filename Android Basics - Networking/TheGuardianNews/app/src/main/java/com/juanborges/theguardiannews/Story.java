package com.juanborges.theguardiannews;

import android.graphics.Bitmap;

public class Story {
    private String title;
    private String date;
    private String section;
    private String pillar;
    private String url;

    public Story(String title, String date, String section, String pillar, String url)
    {
        this.title = title;
        this.date = date;
        this.section = section;
        this.pillar = pillar;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getPillar() {
        return pillar;
    }

    public String getUrl() {
        return url;
    }
}
