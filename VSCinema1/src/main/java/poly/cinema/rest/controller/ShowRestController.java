package poly.cinema.rest.controller;
import java.text.ParseException;
import java.util.List;

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

import poly.cinema.dto.ShowtimesRequest;
import poly.cinema.entity.Show;
import poly.cinema.service.CinemaService;
import poly.cinema.service.RoomService;
import poly.cinema.service.ShowService;

@RestController
@RequestMapping("/rest/show")
@CrossOrigin("*")
public class ShowRestController {
@Autowired ShowService showservice;
@Autowired RoomService roomservice;
@Autowired CinemaService cinemaservice;

	@GetMapping()
	public List<Show> getAll(){
		return showservice.getAll();
	}
	
	@GetMapping("/date")
	public List<ShowtimesRequest> getDate(){
		return showservice.getDate();
	}
	
	@GetMapping("/checkshow/{id}")
	public Show getShow(@PathVariable("id") Integer id) throws ParseException{
		return showservice.getShow(id);
	}
	
	@PostMapping("/showtimes/{id}")
	public List<Show> getShowDate(@PathVariable("id") Integer id, @RequestBody ShowtimesRequest showtimes) throws ParseException{
		return showservice.getShowDate(id, showtimes);
	}
	
	@GetMapping("/censor")
	public List<Show> getShow(){
		return showservice.getShowCensor();
	}
	
	@GetMapping("/listshowdate")
	public List<String> listshowdate(){
		return showservice.listShowDate();
	}
	
	@PostMapping("")
	public Show create(@Valid @RequestBody Show show){
	Show create = showservice.create(show);
	return create;
	}
	
	@PutMapping()
	public Show update(@RequestBody Show show) {
		return showservice.update(show);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Integer id) {
		showservice.deleteById(id);
	}
	
	
	@PostMapping("/fillmovie/{movieid}")
	public List<Show> fillByMovie(@PathVariable("movieid") Integer movieid){
		return showservice.fillByMovie(movieid);
	}

	@PostMapping("/fillroom/{roomid}")
	public List<Show> fillByRoom(@PathVariable("roomid") Integer roomid){
		String roomname = roomservice.getNameByID(roomid);
		//System.out.println(roomname);
		return showservice.fillByRoom(roomname);
	}
	@PostMapping("/fillmovieandroom/{movieid}/{roomid}")
	public List<Show> fillByMovieAndRoom(@PathVariable("movieid") Integer movieid, @PathVariable("roomid") String roomname){
		return showservice.fillByMovieAndRoom(movieid, roomname);
	}}
