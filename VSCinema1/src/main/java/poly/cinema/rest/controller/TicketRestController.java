package poly.cinema.rest.controller;

import java.util.List;

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



import poly.cinema.entity.Ticket;
import poly.cinema.service.TicketService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/ticket")
public class TicketRestController {

	@Autowired
	TicketService ticketService;
	
	@GetMapping()
	public List<Ticket> findAll() { 
		return ticketService.findAll();
	}
	
	@GetMapping("/{id}")
	public List<Ticket> getTicket(@PathVariable("id") Integer id){
		return ticketService.getByShowId(id);
	}
	
}