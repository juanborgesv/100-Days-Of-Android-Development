package com.juanborges.booklisting;

import android.graphics.Bitmap;

public class Book {

    String title;
    String author;
    String year;
    Bitmap image;
    String infoLink;

    Book (String myTitle, String myAuthor, String myYear, Bitmap myImage, String website) {
        title = myTitle;
        author = myAuthor;
        year = myYear;
        image = myImage;
        infoLink = website;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getInfoLink() {
        return infoLink;
    }
}
