package poly.cinema.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import poly.cinema.dto.SignupRequest;
import poly.cinema.entity.Account;
import poly.cinema.entity.Room;
import poly.cinema.entity.Token;
import poly.cinema.service.AccountService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/users")
public class AccountRestController {
	@Autowired HttpServletRequest req;
	@Autowired
	AccountService accountService;
	
	@GetMapping()
	public List<Account> getUsers() {
		return accountService.finAll();
	}
	
	@GetMapping("/getUser")
	public Account getUser() {
		String id= req.getRemoteUser();
		return accountService.findById(id);
		//return req.getRemoteUser();
	}
	
	@GetMapping("/account")
	public Account userLogin() {
		return accountService.getUserLogin();
	}
	
	@PostMapping("/signup")
	public Account create(@RequestBody @Valid SignupRequest signup) {
		return accountService.create(signup);
	}
	
	@GetMapping("/update")
	public Account update() {
		return accountService.getAccount();
	}
	
	@PutMapping("/update")
	public Account update(@RequestBody Account accounnt) {
		return accountService.update(accounnt);
	}
	
	@GetMapping("/change")
	public Account changePassword() {
		return accountService.getAccount();
	}
	
	@GetMapping("/confirm")
	public Account confirm() {
		return accountService.getAccount();
	}
	
	@PutMapping("/change")
	public Account changePassword(@RequestBody Token token) {
		return accountService.changePassword(token);
	}
	
	@GetMapping("/forgot")
	public List<Account> forgotPassword() {
		return accountService.findAll();
	}
	
	@PutMapping("/forgot")
	public Account forgotPassword(@RequestBody Token token) {
		return accountService.forgotPassword(token);
	}
	
	@PostMapping
	public Account save(@RequestBody @Valid Account account) {
		return accountService.save(account);
	}
	

	
	
	@PutMapping("{username}")
	public Account update(@PathVariable("username") String username,@RequestBody @Valid Account account) {
		return accountService.update(account);
	}
	
	@DeleteMapping("{username}" )
	public void delete(@PathVariable("username") String username ) {
		 accountService.delete(username);
	}
	
	
}