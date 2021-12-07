package poly.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FoodController {
	@RequestMapping("/food/booking-food")
	public String doan () {
		return "/food/booking-food";
	}
	
	@RequestMapping("/food/checkout")
	public String checkout () {
		return "/food/checkout";
	}
}
