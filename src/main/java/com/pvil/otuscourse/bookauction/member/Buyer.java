package com.pvil.otuscourse.bookauction.member;

import com.pvil.otuscourse.bookauction.domain.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Покупатель - участник аукциона
 */
public class Buyer {
    /**
     * Торгуемся ли за лот, или пропускаем его
     */
    private boolean skip;

    /**
     * Всего денег на покупки
     */
    private int account;

    /**
     * Номер участника
     */
    private int number;

    /**
     * Приобретенные книги
     */
    private List<Book> saleBooks = new ArrayList<>();

    public Buyer(int number, int account) {
        this.account = account;
        this.number = number;
    }

    public Buyer(int number) {
        this.number = number;
        this.account = 100000 + (int)(Math.random() * 1000000);
    }

    /**
     * Сделать предложение выше СТАРТОВОЙ цены книги,
     * если есть желание купить книгу и хватает денег
     * @param book
     * @return Новая цена или 0 если не хочет купить
     */
    public int offer(Book book) {
        skip = false;
        return newOffer(book.getStartPrice(), book.getStartPrice());
    }

    /** Сделать предложение выше ПОСЛЕДНЕЙ МАКСИМАЛЬНОЙ цены
      * если есть желание купить книгу и хватает денег
     * @param book
     * @return Новая цена или 0 если не хочет купить
     */
    public int offer(Book book, int bestOffer) {
        return newOffer(book.getStartPrice(), bestOffer);
    }

    public int getNumber() {
        return number;
    }

    /**
     * Выйграли торг за лот.
     * @param book Книгу купили
     * @param price За цену
     */
    public void win(Book book, int price) {
        saleBooks.add(book);
        account -= price;
    }

    /**
     * Приобретенные книги
     * @return Пустой или заполненный список, не null
     */
    public List<Book> getSaleBooks() {
        return saleBooks;
    }

    private int newOffer(int startPrice, int bestOffer) {
        //Не потеряли ли еще интерес к лоту?
        skip = skip || Math.random() < 0.2;
        if (!skip) {
            //Вступаем в торг не всегда, а с некоторой вероятностью, пропорционально увеличению от стартовой цены
            if (Math.random() > bestOffer / (startPrice * 10.0)) {
                //Увеличиваем цену на величину от 100 до 1000
                int res = bestOffer + 100 * (int) (1 + Math.random() * 9);
                if (account > res)
                    return res;
            }
        }
        return 0;
    }

}
