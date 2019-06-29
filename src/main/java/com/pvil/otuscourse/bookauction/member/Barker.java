package com.pvil.otuscourse.bookauction.member;

import com.pvil.otuscourse.bookauction.domain.Book;
import com.pvil.otuscourse.bookauction.domain.Offer;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Ведущий аукциона
 */
@Component
public class Barker {
    private static final String[] KNOCKS_AS_STRING = new String[] {"РАЗ", "ДВА", "ТРИ"};

    /**
     * Текущее лучшее предложение. М.б. null
     */
    private Offer bestOffer;

    /**
     * Стартовая цена, ниже нее заявки не принимаются
     */
    private int startPrice;

    /**
     * Сколько раз ударили молотком для текущей цены
     */
    private int knocks;

    public void newLot(Book book) {
        bestOffer = null;
        knocks = 0;
        startPrice = book.getStartPrice();
    }

    /**
     * Получить предложения цены от участников аукциона
     * @param offer
     * @return {@code true} если "от предложения нельзя было отказаться"
     */
    public boolean offer(Offer offer) {
        if (bestOffer == null || offer.getOffer() > bestOffer.getOffer()) {
            bestOffer = offer;
            knocks = 0;
            return true;
        }
        return false;
    }

    /**
     * Лучшее предложение за лот на текущий момент
     */
    public Optional<Integer> getBestOffer() {
        return bestOffer != null
                ? Optional.of(bestOffer.getOffer())
                : Optional.empty();
    }

    /**
     * Номер участника с лучшим предложением за лот на текущий момент
     */
    public Optional<Integer> getBestOfferMember() {
        return bestOffer != null
                ? Optional.of(bestOffer.getBuyer())
                : Optional.empty();
    }

    /**
     * Продано?
     * @return
     */
    public boolean isSold() {
        return knocks == 3;
    }

    /**
     * Для текущей цены сколько ударили молотком
     * @return
     */
    public int getKnockCount() {
        return knocks;
    }

    /**
     * Для текущей цены сколько ударили молотком
     * @return
     */
    public String getKnockCountAsString() {
        return knocks == 0
                ? ""
                : KNOCKS_AS_STRING[knocks - 1];
    }

    /**
     * Стукнули молотком, 3 удара - и продано
     * @return
     */
    public int knock() {
        if (knocks < 3)
            knocks++;
        return knocks;
    }
}
