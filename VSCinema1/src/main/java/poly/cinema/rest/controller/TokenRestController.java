package poly.cinema.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.dto.TokenRequest;
import poly.cinema.entity.Token;
import poly.cinema.service.TokenService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/tokens")
public class TokenRestController {

	@Autowired
	TokenService tokenService;
	
	@GetMapping("/change")
	public Token getTokens() {
		return tokenService.findByUsername();
	}
	
	@PostMapping("/change")
	public Token change(@RequestBody TokenRequest tokenRequest) {
		return tokenService.change(tokenRequest);
	}
	
	@GetMapping("/forgot")
	public Token getTokenForgot() {
		return tokenService.getTokenForgot();
	}
	
	@PostMapping("/forgot")
	public Token forgot(@RequestBody TokenRequest tokenRequest) {
		return tokenService.forgot(tokenRequest);
	}
	
}