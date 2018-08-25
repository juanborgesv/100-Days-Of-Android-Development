package com.juanborges.booklisting;

public class Book {

    String title;
    String author;
    String year;
    String imageUrl;

    Book (String myTitle, String myAuthor, String myYear, String myImageUrl) {
        title = myTitle;
        author = myAuthor;
        year = myYear;
        imageUrl = myImageUrl;
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
}
