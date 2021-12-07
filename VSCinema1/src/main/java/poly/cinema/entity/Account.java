package poly.cinema.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Authority;


@SuppressWarnings("serial")
@Data
@NoArgsConstructor @AllArgsConstructor
@Entity 
@Table(name = "users")
public class Account  implements Serializable{
	@Id
	@NotEmpty(message = "Vui lòng nhập password ")
	String username;
	
	@NotEmpty(message = "Vui lòng nhập password ")
	String password;
	
	@NotEmpty(message = "Vui lòng nhập Họ và Tên")
	String fullname;
	
	@NotEmpty(message = "Vui lòng nhập email")
	String email;
	
	@NotEmpty(message = "Vui lòng nhập sdt")	
	String phone ;
	
	Boolean gender ;
	
	@NotEmpty(message = "Vui lòng nhập cmnd")
	String cmnd ;
	
	@NotEmpty(message = "Vui lòng nhập địa chỉ")
	String address ;
	String image;
	Boolean activity;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Order> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Authority> authorities;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Token> tokens;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<PriceHistory> priceHistories ;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Censor> censors;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Show> shows ;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<TicketCoupon> ticketCoupons;

	public Account(String username, String password, String fullname, String email, String phone, Boolean gender,
			String cmnd, String address, String image, Boolean activity) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.cmnd = cmnd;
		this.address = address;
		this.image = image;
		this.activity = activity;
	}
	
}