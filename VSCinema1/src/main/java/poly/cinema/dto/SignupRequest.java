package poly.cinema.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class SignupRequest {
	@NotEmpty(message = "Vui lòng nhập username ")
	private String username;
	
	@NotEmpty(message = "Vui lòng nhập password ")	
	private String password;
	
	@NotEmpty(message = "Vui lòng nhập họ và tên ")	
	private String fullname;
	
	@NotEmpty(message = "Vui lòng nhập email ")	
	private String email;
	
	@NotEmpty(message = "Vui lòng nhập SĐT ")	
	private String phone ;
	private Boolean gender;
	
	@NotEmpty(message = "Vui lòng nhập password ")	
	private String cmnd;
	
	@NotEmpty(message = "Vui lòng nhập address ")	
	private String address;
	private String image;
	private Boolean activity;
	private List<String> role;
	
}
