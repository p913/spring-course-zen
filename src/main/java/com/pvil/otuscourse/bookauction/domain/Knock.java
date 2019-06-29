package com.pvil.otuscourse.bookauction.domain;

/**
 * Сообщение о максимальном предложении цены с ударом молотка ведущего
 */
public class Knock {
    private Book book;

    private Offer bestOffer;

    private int knockCount;

    public Knock(Book book, Offer bestOffer, int knockCount) {
        this.book = book;
        this.bestOffer = bestOffer;
        this.knockCount = knockCount;
    }

    public Book getBook() {
        return book;
    }

    public Offer getBestOffer() {
        return bestOffer;
    }

    public int getKnockCount() {
        return knockCount;
    }

    @Override
    public String toString() {
        return "Knock{" +
                "book=" + book +
                ", bestOffer=" + bestOffer +
                ", knockCount=" + knockCount +
                '}';
    }
}
