package com.pvil.otuscourse.bookauction.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.MessageChannels;


@Configuration
public class ConfigChannels {

    /**
     * Обявление нового лота всем участникам и начало торга
     * @return
     */
    @Bean
    public PublishSubscribeChannel lot() {
        return MessageChannels
                .publishSubscribe()
                .get();
    }

    /**
     * Предложения от покупателей
     * @return
     */
    @Bean
    public QueueChannel offers() {
        return MessageChannels
                .queue()
                .get();
    }

    /**
     * Ведущий объявляет покупателям текущее лучшее предложение и ведет отсчет (стукает молотком)
     * @return
     */
    @Bean
    public PublishSubscribeChannel knock() {
        return MessageChannels
                .publishSubscribe()
                .get();
    }

    /**
     * Ведущий объявляет покупателям выигравшего в торге за лот, ему достается книга
     * @return
     */
    @Bean
    public PublishSubscribeChannel winner() {
        return MessageChannels
                .publishSubscribe()
                .get();
    }


}
