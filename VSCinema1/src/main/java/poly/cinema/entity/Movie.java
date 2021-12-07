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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "movies")
public class Movie  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String poster;
	String trailer ;
	String summary;
	String language ;
	String cast ;
	String director ;
	Boolean activity;
	String banner ;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Releasedate")
	Date releaseDate = new Date();
	
	@Temporal(TemporalType.TIME)
	@Column(name = "Runningtime")
	Date runningTime = new Date();

	@ManyToOne
	@JoinColumn(name = "Genresid")
	Genre genre;
	
	@ManyToOne
	@JoinColumn(name = "Ratedsid")
	Rated rated;
	

	
	
	@JsonIgnore
	@OneToMany(mappedBy = "movie")
	List<Show> shows;	
	
	
}