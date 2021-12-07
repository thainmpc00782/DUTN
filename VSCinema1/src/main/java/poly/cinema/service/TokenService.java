package poly.cinema.service;

import poly.cinema.dto.TokenRequest;
import poly.cinema.entity.Token;

public interface TokenService {

	Token change(TokenRequest tokenRequest);

	Token findByUsername();

	Token forgot(TokenRequest tokenRequest);

	Token getTokenForgot();

}