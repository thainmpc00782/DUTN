package poly.cinema.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.AccountDAO;
import poly.cinema.dao.PriceHistoryDAO;
import poly.cinema.dto.PriceHistoryRequest;
import poly.cinema.entity.Account;
import poly.cinema.entity.Food;
import poly.cinema.entity.PriceHistory;
import poly.cinema.service.PriceHistoryService;

@Service
public class PriceHistoryServiceImpl implements PriceHistoryService {

	@Autowired
	PriceHistoryDAO priceHistoryDao;

	@Autowired
	AccountDAO accountDAO;

	@Autowired
	HttpServletRequest request;

	@Override
	public List<PriceHistory> findAll() {
		// TODO Auto-generated method stub
		return priceHistoryDao.findAll();
	}

	@Override
	public PriceHistory findById(Integer id) {
		// TODO Auto-generated method stub
		return priceHistoryDao.findById(id).get();
	}

	@Override
	public PriceHistory create(PriceHistoryRequest pricehistory) {
		// TODO Auto-generated method stub
		String user = request.getRemoteUser(); // Lấy user đang đăng nhập
		Account account = accountDAO.findById(user).get(); // Tìm user trong csdl

		PriceHistory priceHis = new PriceHistory(account, pricehistory.getFood(), pricehistory.getPrice(),
				pricehistory.getDate(), pricehistory.getDate());
		return priceHistoryDao.save(priceHis);
	}

	@Override
	public List<Food> findByFood(Integer idfood) {
		// TODO Auto-generated method stub
		return priceHistoryDao.findByFood(idfood);
	}

	@Override
	public List<Account> findByAccount(String username) {
		// TODO Auto-generated method stub
		return priceHistoryDao.findByAccount(username);
	}

}