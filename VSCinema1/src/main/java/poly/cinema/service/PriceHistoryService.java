package poly.cinema.service;

import java.util.List;

import poly.cinema.dto.PriceHistoryRequest;
import poly.cinema.entity.Account;
import poly.cinema.entity.Food;
import poly.cinema.entity.PriceHistory;

public interface PriceHistoryService {

	List<PriceHistory> findAll();

	PriceHistory findById(Integer id);

	PriceHistory create(PriceHistoryRequest pricehistory);

	List<Food> findByFood(Integer idfood);

	List<Account> findByAccount(String username);

}