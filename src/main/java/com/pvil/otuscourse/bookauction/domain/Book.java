package com.pvil.otuscourse.bookauction.domain;

public class Book {
    private String title;

    private String author;

    private String genre;

    private String publisher;

    private int year;

    private int startPrice;

    public Book(String title, String author, String genre, String publisher, int year, int startPrice) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.year = year;
        this.startPrice = startPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", startPrice=" + startPrice +
                '}';
    }
}
