package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Account;
import poly.cinema.entity.Food;
import poly.cinema.entity.PriceHistory;

public interface PriceHistoryDAO extends JpaRepository<PriceHistory, Integer> {

	@Query("SELECT f FROM PriceHistory f WHERE f.food.id = :idfood")
	List<Food> findByFood(Integer idfood);

	@Query("SELECT p FROM PriceHistory p WHERE p.user.username = :username")
	List<Account> findByAccount(String username);

}