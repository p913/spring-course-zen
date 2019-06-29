package com.pvil.otuscourse.bookauction.integration;

import com.pvil.otuscourse.bookauction.domain.Book;
import com.pvil.otuscourse.bookauction.domain.Knock;
import com.pvil.otuscourse.bookauction.domain.Offer;
import com.pvil.otuscourse.bookauction.domain.Winner;
import com.pvil.otuscourse.bookauction.member.Barker;
import com.pvil.otuscourse.bookauction.member.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Configuration
@IntegrationComponentScan
@EnableIntegration
public class ConfigFlows {
    private static final int NUMBER_OF_BUYERS = 20;

    @Autowired
    private final PublishSubscribeChannel lot;

    @Autowired
    private final QueueChannel offers;

    @Autowired
    private final PublishSubscribeChannel knock;

    @Autowired
    private final PublishSubscribeChannel winner;

    public ConfigFlows(PublishSubscribeChannel lot, QueueChannel offers, PublishSubscribeChannel knock, PublishSubscribeChannel winner) {
        this.lot = lot;
        this.offers = offers;
        this.knock = knock;
        this.winner = winner;
    }

    @PostConstruct
    public void createBayers() {
        for (int i = 0; i < NUMBER_OF_BUYERS; i++) {
            Buyer buyer = new Buyer(i + 1);

            lot.subscribe(message -> {
                //Обявлен новый лот, начало торга
                int offer = buyer.offer((Book) message.getPayload());
                if (offer > 0)
                    offers.send(new GenericMessage<Offer>(new Offer(offer, buyer.getNumber())));
            });

            knock.subscribe(message -> {
                //Ведущий обявляет максимальную цену с ударом молотка. Покупатель может сделать новое предложение
                Knock knock = (Knock) message.getPayload();
                if (knock.getKnockCount() < 3
                        && knock.getBestOffer().getBuyer() != buyer.getNumber() /*Свою цену не перебиваем*/) {
                    int offer = buyer.offer(knock.getBook(), knock.getBestOffer().getOffer());
                    if (offer > 0)
                        offers.send(new GenericMessage<Offer>(new Offer(offer, buyer.getNumber())));
                }
            });

            winner.subscribe(message -> {
                Winner winner = (Winner) message.getPayload();
                if (winner.getBuyer() == buyer.getNumber())
                    buyer.win(winner.getBook(), winner.getPrice());
            });
        }
    }

    @Bean
    public IntegrationFlow lotsFlow(GenericHandler<Book> oneLotDealHandler) {
        //Создание как здесь, а не через IntegrationFlows.from содает канал lotsFlow.input,
        //на него можно сослаться в gateway
        return f -> f
                .split()
                .channel("lot") //обявляем покупателям новый лот
                .<Book>handle(oneLotDealHandler)
                .aggregate() //подсчитываем выручку за торговую сессию
                .<Collection<Integer>, Integer>transform(c -> c.stream().reduce(0, Integer::sum));
    }

    @Bean
    public GenericHandler<Book> oneLotDealHandler(Barker barker) {
        return new GenericHandler<Book>() {

            @Override
            public Object handle(Book book, MessageHeaders headers) {
                System.out.println("Продается ЛОТ: " + book);
                barker.newLot(book);

                while (!barker.isSold()) {
                    //В течение некоторого времени собираем предложения
                    Message m = offers.receive(1000);
                    if (m != null) {
                        if (barker.offer((Offer) m.getPayload()))
                            System.out.println("Покупатель №" + barker.getBestOfferMember().get()
                                    + " предложил " + barker.getBestOffer().get());
                    } else {
                        //Если предложения закончились ...
                        if (barker.getBestOffer().isPresent()) {
                            //объявляем самую высокую цену, и номер назвавшего, стукаем молотком ))
                            barker.knock();
                            System.out.println("ТЮК! " + barker.getBestOffer().get() + " " + barker.getKnockCountAsString() + "!");
                            knock.send(
                                    new GenericMessage<Knock>(
                                            new Knock(book,
                                                    new Offer(barker.getBestOffer().get(), barker.getBestOfferMember().get()),
                                                    barker.getKnockCount())));
                            //Три удара молотка - продано, обявляем победителя
                            if (barker.isSold()) {
                                System.out.println("Продано! ЛОТ уходит покупателю №" + barker.getBestOfferMember().get()
                                        + " за " + barker.getBestOffer().get() + " при стартовой цене " + book.getStartPrice());
                                winner.send(
                                        new GenericMessage<Winner>(
                                                new Winner(book, barker.getBestOfferMember().get(), barker.getBestOffer().get())));
                                break;
                            }
                        } else {
                            //Предложений вообще нет - переходим к следующему лоту
                            System.out.println("Нет предложений и ЛОТ снимается с торгов");
                            break;
                        }
                    }
                }
                return barker.getBestOffer().orElse(0); //Возвращаем за сколько продали
            }
        };
    }
}

