package poly.cinema.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity 
@Setter
@Getter
@Table(name = "shows")
public class Show  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Showdate")
	Date showDate = new Date();
	
	@Temporal(TemporalType.TIME)
	@Column(name = "Starttime")
	Date startTime = new Date();
	
	@Temporal(TemporalType.TIME)
	@Column(name = "Endtime")
	Date endTime = new Date();

	Integer Activity;
	
	@ManyToOne
	@JoinColumn(name = "Moviesid")
	Movie movie;
	@ManyToOne
	@JoinColumn(name = "Roomsid")
	Room room;
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Account user;
	
	@ManyToOne
	@JoinColumn(name = "Priceid")
	PriceShow priceShow;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "show")
	List<Ticket> tickets;
	
	@JsonIgnore
	@OneToMany(mappedBy = "show")
	List<Censor> censors ;

	

	
}