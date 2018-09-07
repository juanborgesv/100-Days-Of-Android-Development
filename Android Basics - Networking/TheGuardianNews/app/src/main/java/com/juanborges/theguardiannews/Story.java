package com.juanborges.theguardiannews;

public class Story {
    private String title;
    private String date;
    private String section;
    private String pillar;
    private String url;
    private String author;

    public Story
            (String title, String date, String section, String pillar, String url, String author) {
        this.title = title;
        this.date = date;
        this.section = section;
        this.pillar = pillar;
        this.url = url;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }
}
