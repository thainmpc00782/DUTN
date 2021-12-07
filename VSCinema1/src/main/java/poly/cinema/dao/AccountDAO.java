package poly.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.cinema.entity.Account;

public interface AccountDAO extends JpaRepository<Account, String>{

}
