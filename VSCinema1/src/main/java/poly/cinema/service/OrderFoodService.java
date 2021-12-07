package poly.cinema.service;

import java.util.Date;
import java.util.List;

import poly.cinema.entity.OrderFood;

public interface OrderFoodService {
	List<OrderFood> getAll();

	List<Object[]> getSumPriceOrderFood();
	List<OrderFood> getSumFoodByTimes(Date time, Date endtime);
}