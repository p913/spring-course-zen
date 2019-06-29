package com.pvil.otuscourse.bookauction.domain;

/**
 * Объявлен победитель торгов за один лот
 */
public class Winner {
    private Book book;

    private int buyer;

    private int price;

    public Winner(Book book, int buyer, int price) {
        this.book = book;
        this.buyer = buyer;
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public int getBuyer() {
        return buyer;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Winner{" +
                "book=" + book +
                ", buyer=" + buyer +
                ", price=" + price +
                '}';
    }
}
