package poly.cinema.service;

import java.util.List;

import javax.validation.Valid;

import poly.cinema.dto.SignupRequest;
import poly.cinema.entity.Account;
import poly.cinema.entity.Token;

public interface AccountService {

	List<Account> finAll();

	Account findById(String username);

	Account create(SignupRequest signup);

	Account getAccount();

	Account update(Account accounnt);

	Account changePassword(Token token);

	

	Account forgotPassword(Token token);

	void delete(String username);

	List<Account> findAll();

	Account save( Account account);

	Account getUserLogin();


}
