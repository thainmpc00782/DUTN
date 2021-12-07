package poly.cinema.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Room;
import poly.cinema.entity.Show;
import poly.cinema.service.CinemaService;
import poly.cinema.service.RoomService;

@RestController
@RequestMapping("rest/rooms")
public class RoomRestController {

	@Autowired RoomService roomservice;
	@Autowired CinemaService cinemaservice;

	@GetMapping("")
	public List<Room> getAll() {
		return roomservice.getAll();
	}

	@PostMapping
	public Room save(@RequestBody Room room) {
		return roomservice.save(room);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		roomservice.delete(id);
	}
	@PutMapping()
	public Room update(@RequestBody Room room) {
		return roomservice.update(room);
	}
	@GetMapping("{id}")
	public List<Room> getByCinemaId(@PathVariable("id")Integer cinemaid){
		return roomservice.findByCinemaId(cinemaid);
	}
//	@GetMapping("/name")
//	public List<Room> getByName(@RequestParam("name")String name){
//		return roomservice.findByName(name);
//	}
	
	@GetMapping("/name")
	public List<Room> listNameRoom(){
		return roomservice.getListName();
	}
	
	
	@PostMapping("/createfromShow")
	public Room createfromShow(@RequestBody Show show) {
		System.out.println("Room...");
		String roomname = roomservice.getNameByID(show.getRoom().getId());
//		System.out.println(show.getRoom().getId());
		Room r = new Room();
		r.setName(roomname);
		r.setCinema(cinemaservice.getById(show.getRoom().getCinema().getId()));
		r.setActivity(true);
		return roomservice.create(r);
	}
	@GetMapping("/findByCinema/{cinemaid}")
	public List<Room> findByCinema(@PathVariable("cinemaid") Integer cinemaid){
//		System.out.println(cinemaid);
		return roomservice.findByCinemaId(cinemaid);
	}
}