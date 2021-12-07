package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Token;

public interface TokenDAO extends JpaRepository<Token, Integer>{

	@Query("SELECT o FROM Token o WHERE o.user.username=?1")
	List<Token> findByUsername(String username);

}