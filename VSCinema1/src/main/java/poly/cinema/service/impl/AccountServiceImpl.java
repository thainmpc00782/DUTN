package poly.cinema.service.impl;

import java.util.ArrayList;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.common.ERole;
import poly.cinema.dao.AccountDAO;
import poly.cinema.dao.AuthorityDAO;
import poly.cinema.dao.RoleDAO;
import poly.cinema.dao.TokenDAO;
import poly.cinema.dto.SignupRequest;
import poly.cinema.entity.Account;
import poly.cinema.entity.Authority;
import poly.cinema.entity.Role;
import poly.cinema.entity.Token;
import poly.cinema.service.AccountService;
import poly.cinema.service.MailerService;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	AuthorityDAO authorityDAO;
	
	@Autowired
	TokenDAO tokenDAO;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	MailerService mailerService;

	@Override
	public List<Account> finAll() {
		List<Account> account = new ArrayList<Account>();
		String username = request.getRemoteUser();
		for(Account acc:accountDAO.findAll()) {
			if(!acc.getUsername().equals(username)) {
				account.add(acc);
			}
		}
		return account;
	}

	@Override
	public Account findById(String username) {
		return accountDAO.findById(username).get();
	}

	@Override
	public Account create(SignupRequest signup) {
		Account account = new Account(signup.getUsername(), signup.getPassword(), signup.getFullname(), signup.getEmail(), 
				signup.getPhone(), signup.getGender(), signup.getCmnd(), signup.getAddress(), signup.getImage(), signup.getActivity());
		accountDAO.save(account);
		List<String> strRoles = signup.getRole();
		List<Role> roles = new ArrayList<Role>();
		strRoles.forEach(role -> {
			switch(role) {
				case "staff":
					Role staff = roleDAO.findByName(ERole.ROLE_STAFF);
					roles.add(staff);
					break;
				case "mod":
					Role mod = roleDAO.findByName(ERole.ROLE_MODERATOR);
					roles.add(mod);
					break;
				default:
					Role user = roleDAO.findByName(ERole.ROLE_USER);
					roles.add(user);
			}
		});
		roles.forEach(role -> {
			Authority authority = new Authority();
			authority.setUser(account);
			authority.setRole(role);
			authorityDAO.save(authority);
		});
		return account;
	}

	@Override
	public Account getAccount() {
		String username = request.getRemoteUser();
		return accountDAO.findById(username).get();
	}

	@Override
	public Account update(Account accounnt) {
		return accountDAO.save(accounnt);
	}

	@Override
	public Account changePassword(Token token) {
		token.setActivity(false);
		tokenDAO.save(token);
		Account user = token.getUser();
		user.setPassword(token.getPassword());
		return accountDAO.save(user);
	}

	@Override
	public List<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	public Account forgotPassword(Token token) {
		token.setActivity(false);
		tokenDAO.save(token);
		Account user = token.getUser();
		user.setPassword(token.getPassword());
		try {
			mailerService.send(user.getEmail(), "Quên Mật Khẩu", "Mật khẩu mới của bạn là: "+token.getPassword());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return accountDAO.save(user);
	}

	@Override
	public void delete(String username) {
		 accountDAO.deleteById(username);
		
	}

	@Override
	public Account save( Account account) {
		return accountDAO.save(account);
	}

	@Override
	public Account getUserLogin() {
		String username = request.getRemoteUser();
		if(username == null) {
			return null;
		} else {
			return accountDAO.findById(username).get();
		}
	}
}