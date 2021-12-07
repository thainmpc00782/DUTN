package poly.cinema.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import poly.cinema.dto.CreateOrderRequest;
import poly.cinema.dto.OrderRequest;
import poly.cinema.dto.QueryDR;
import poly.cinema.dto.UrlRequest;
import poly.cinema.entity.Order;
import poly.cinema.entity.OrderFood;

public interface OrderService  {
	List<Order> findAll();
	
	List<OrderFood> findByOrderId(String orderid);
	
	Order updateActive(String id,Order order);
	List<Order> findByDate(Date createdate);
	
	List<Object[]> listSumRevenues();
	Long getSumRevenuesOfDay();
	Long getSumRevenuesOfMonth();
	Long getSumRevenuesOfYear();

	UrlRequest createOrder(CreateOrderRequest data) throws UnsupportedEncodingException;

	List<OrderRequest> findByActivity();

	QueryDR checkOrder(String id) throws IOException;
}