package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Seat;
import poly.cinema.service.SeatService;

@RestController
@RequestMapping("rest/seats")
public class SeatRestController {
	@Autowired
	SeatService seatService;
	
	@GetMapping("{id}")
	public List<Seat> getByRoomsId(@PathVariable("id")Integer roomid){
		return seatService.findByRoomId(roomid);
	}
	
	@PostMapping()
	public Seat save(@RequestBody Seat seat) {
		return seatService.create(seat);
	}
	
	@PutMapping()
	public Seat update(@RequestBody Seat seat) {
		return seatService.update(seat);
	}
	
}