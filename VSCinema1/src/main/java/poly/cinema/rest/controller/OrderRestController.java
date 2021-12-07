package poly.cinema.rest.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.dto.CreateOrderRequest;
import poly.cinema.dto.OrderRequest;
import poly.cinema.dto.QueryDR;
import poly.cinema.dto.UrlRequest;
import poly.cinema.entity.Order;
import poly.cinema.entity.OrderFood;
import poly.cinema.service.OrderService;

@RestController
@RequestMapping("rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;
	@GetMapping
	public List<Order> getAll() {
		return orderService.findAll();
	}
	
	@GetMapping("/checkOrder")
	public List<OrderRequest> getOrder() {
		return orderService.findByActivity();
	}
	
	@GetMapping("/checkOrder/{id}")
	public QueryDR check(@PathVariable("id") String id) throws IOException {
		return orderService.checkOrder(id);
	}
	
	@GetMapping("{orderid}")
	public List<OrderFood> findByOrderId(@PathVariable("orderid")String orderid) {
		return orderService.findByOrderId(orderid);
	}
	@PutMapping("{id}")
	public Order updateActive(@PathVariable("id") String id,@RequestBody Order order) {
		return orderService.updateActive(id,order);
	}
	
	@GetMapping("/findbydate/{createdate}")
	public List<Order> findByDate(@PathVariable("createdate")Date createdate) {
		return orderService.findByDate(createdate);
	}
	
	@PostMapping("/create")
	public UrlRequest createOrder(@RequestBody CreateOrderRequest data) throws UnsupportedEncodingException{
		return orderService.createOrder(data);
	}
	
	
}