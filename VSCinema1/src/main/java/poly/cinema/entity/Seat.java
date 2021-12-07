package poly.cinema.entity;

import java.io.Serializable;
import java.sql.Time;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "seats")
public class Seat  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	Integer position;
	Boolean activity;
	
	@ManyToOne
	@JoinColumn(name = "Roomsid")
	Room room;
	
	@ManyToOne
	@JoinColumn(name = "Typesid")
	Type type;
	
	@JsonIgnore
	@OneToMany(mappedBy = "seat")
	List<Ticket> tickets;
	
	

	
	

	
}