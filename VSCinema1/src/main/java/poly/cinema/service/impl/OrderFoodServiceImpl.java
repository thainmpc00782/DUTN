package poly.cinema.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.OrderFoodDAO;
import poly.cinema.entity.OrderFood;
import poly.cinema.service.OrderFoodService;
@Service
public class OrderFoodServiceImpl implements OrderFoodService{
@Autowired OrderFoodDAO orderfooddao;
	@Override
	public List<OrderFood> getAll() {
		// TODO Auto-generated method stub
		return orderfooddao.findAll();
	}
	@Override
	public List<Object[]> getSumPriceOrderFood() {
		// TODO Auto-generated method stub
		return orderfooddao.getSumPriceOrderFood();
	}
	@Override
	public List<OrderFood> getSumFoodByTimes(Date time, Date endtime) {
		// TODO Auto-generated method stub
		return orderfooddao.getSumFoodByTimes(time, endtime);
	}

}