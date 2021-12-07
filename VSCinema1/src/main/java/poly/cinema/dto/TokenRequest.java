   
package poly.cinema.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Account;

@Data
@NoArgsConstructor @AllArgsConstructor
public class TokenRequest {

	Account user;
	String password;
	Date expiry;
	Integer category;
	Boolean activity;
}