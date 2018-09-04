package com.juanborges.theguardiannews;

import android.graphics.Bitmap;

public class Story {
    private String title;
    private String author;
    private String date;
    private String section;
    private Bitmap image;
    private String url;

    public Story(String title, String author, String date, String section, Bitmap image, String url)
    {
        this.title = title;
        this.author = author;
        this.date = date;
        this.section = section;
        this.image = image;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
