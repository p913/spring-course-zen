package com.pvil.otuscourse.bookauction.integration;

import com.pvil.otuscourse.bookauction.domain.Book;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Collection;

@MessagingGateway
public interface AuctionGateway {
    @Gateway(requestChannel = "lotsFlow.input")
    int sale(Collection<Book> book);
}
