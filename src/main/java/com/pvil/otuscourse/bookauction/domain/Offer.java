package com.pvil.otuscourse.bookauction.domain;

/**
 * Предложение цены от покупателей
 */
public class Offer {
    /**
     * Предложение цены
     */
    private int offer;

    /**
     * Номер участника, сделавшего предложение
     */
    private int buyer;

    public Offer(int offer, int buyer) {
        this.offer = offer;
        this.buyer = buyer;
    }

    public int getOffer() {
        return offer;
    }

    public int getBuyer() {
        return buyer;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offer=" + offer +
                ", buyer=" + buyer +
                '}';
    }
}
