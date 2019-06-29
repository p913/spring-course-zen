package com.pvil.otuscourse.bookauction;

import com.pvil.otuscourse.bookauction.domain.Book;
import com.pvil.otuscourse.bookauction.integration.AuctionGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BookAuctionApplication {

	static List<Book> lots =  Arrays.asList(
			new Book("Harry Potter and the Sorcerer's Stone", "J. K. Rowling",
					"Fantasy", "Scholastic, Inc.", 1999, 1000),
			new Book("Harry Potter and the Goblet of Fire", "J. K. Rowling",
					"Fantasy", "Scholastic, Inc.", 2013, 2000),
			new Book("Solaris", "Stanislav Lem",
					"Science fiction", "Mariner", 2002, 3000),
			new Book("The Invincible", "Stanislav Lem",
					"Science fiction", "Pro Auctore Wojciech Zemek", 2017, 4000),
			new Book("Anna Karenina", "Lev Tolstoy",
					"Drama", "Modern Library", 2000, 5000),
			new Book("War and Peace", "Lev Tolstoy",
					"Drama", "Vintage", 2008, 6000),
			new Book("Eugene Onegin: A Novel in Verse", "A.S. Pushkin",
					"Poetry", "Oxford University Press", 2009, 7000));

	public static void main(String[] args) {
		ApplicationContext ctx =  SpringApplication.run(BookAuctionApplication.class, args);

		AuctionGateway auction = ctx.getBean(AuctionGateway.class);

		int profit = auction.sale(lots);

		System.out.println("За торговый день аукцион выручил " + profit);

		System.exit(0);
	}

}
