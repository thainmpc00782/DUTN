package poly.cinema.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.dto.PriceHistoryRequest;
import poly.cinema.entity.PriceHistory;
import poly.cinema.service.PriceHistoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("rest/priceHistory")
public class PriceHistoryRestController {

	@Autowired
	PriceHistoryService priceHistoryService;
	
	@PostMapping
	public PriceHistory create(@RequestBody PriceHistoryRequest priceHisRequest) { 
		return priceHistoryService.create(priceHisRequest);
	}
	
}