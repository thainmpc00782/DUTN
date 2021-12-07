package poly.cinema.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.AccountDAO;
import poly.cinema.dao.FoodDao;
import poly.cinema.dao.PriceHistoryDAO;
import poly.cinema.dao.SaleDAO;
import poly.cinema.dto.FoodRequest;
import poly.cinema.entity.Account;
import poly.cinema.entity.Food;
import poly.cinema.entity.PriceHistory;
import poly.cinema.entity.Sale;
import poly.cinema.service.FoodService;
import poly.cinema.service.PriceHistoryService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodDao foodDao;
	@Autowired
	HttpServletRequest request;

	@Autowired
	AccountDAO accountDao;
	
	@Autowired
	PriceHistoryDAO historyDAO;
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@Autowired
	SaleDAO saleDAO;

	@Override
	public List<Food> findAll() {
		// TODO Auto-generated method stub
		String user = request.getRemoteUser();
		System.out.println("Usser: " + user);

		return foodDao.findAll();
	}

	@Override
	public Food findById(Integer id) {
		// TODO Auto-generated method stub
		return foodDao.findById(id).get();
	}

	@Override
	public Food create(Food food) {
		// TODO Auto-generated method stub
		String user = request.getRemoteUser(); // Lấy user đang login 
		Account account = accountDao.findById(user).get(); // Tìm user trong csdl
		
		foodDao.save(food);
	
		return food;
	}

	@Override
	public Food update(Food food) {
		// TODO Auto-generated method stub
		return foodDao.save(food);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		foodDao.deleteById(id);
	}
	
	@Override
	public List<FoodRequest> getFoodCinema(Integer id) {
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		List<FoodRequest> foodRequest = new ArrayList<FoodRequest>();
		List<Food> foodList = foodDao.findByCinemaId(id);
		foodList.forEach(f -> {
			FoodRequest food = new FoodRequest();
			food.setFood(f);
			List<Sale> saleList = saleDAO.findByFoodID(f.getId());
			saleList.forEach(s -> {
				if(s.getEndDate().getTime() >= cld.getTime().getTime()) {
					food.setSale(s);
				}
			});
			foodRequest.add(food);
		});
		return foodRequest;
	}

}