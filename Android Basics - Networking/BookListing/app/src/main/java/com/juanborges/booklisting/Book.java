package com.juanborges.booklisting;

public class Book {

    String title;
    String author;
    String year;
    String imageUrl;
    String infoLink;

    Book (String myTitle, String myAuthor, String myYear, String myImageUrl, String website) {
        title = myTitle;
        author = myAuthor;
        year = myYear;
        imageUrl = myImageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInfoLink() {
        return infoLink;
    }
}
