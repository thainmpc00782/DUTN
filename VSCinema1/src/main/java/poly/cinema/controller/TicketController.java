package poly.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketController {
	@RequestMapping("ticket/book-tickets")
	public String ticket() {
		return "ticket/book_tickets";
	}
	@RequestMapping("ticket/bookfood")
	public String bookfood() {
		return "ticket/food";
	}
	@RequestMapping("ticket/checkout")
	public String checkout() {
		return "ticket/checkout";
	}
}
