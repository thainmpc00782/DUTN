package poly.cinema.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Account;
import poly.cinema.entity.AccountSum;
import poly.cinema.entity.Authority;
import poly.cinema.entity.Movie;
import poly.cinema.entity.OrderFood;
import poly.cinema.entity.Room;
import poly.cinema.entity.SumRevenuesByCinema;
import poly.cinema.entity.Ticket;
import poly.cinema.entity.Top5Movie;
import poly.cinema.service.AccountService;
import poly.cinema.service.AuthorityService;
import poly.cinema.service.MovieService;
import poly.cinema.service.OrderFoodService;
import poly.cinema.service.OrderService;
import poly.cinema.service.RoomService;
import poly.cinema.service.TicketService;

@RestController
@RequestMapping("/rest/statistics")
public class StatisticsRestController {
	@Autowired TicketService ticketservice;
	@Autowired OrderFoodService orderfoodservice;
	@Autowired AccountService accountservice;
	@Autowired AuthorityService authorityservice;
	@Autowired RoomService roomservice;
	@Autowired OrderService orderservice;
	@Autowired MovieService movieservice;
	@GetMapping("")
	public List<Ticket> getAll(){
		return ticketservice.getAll();
	}
	@GetMapping("/getlistdate")
	public List<String> getDate(){
		return ticketservice.getListDate();
	}
	@GetMapping("/getlistmovie")
	public List<Movie> getListMovie(){
		return ticketservice.getListMovie();
	}
	
	@GetMapping("/food")
	public List<OrderFood> getListOrderFood(){
		return orderfoodservice.getAll();
	}
	@GetMapping("/accounts")
	public List<Authority> getListAccounts(){
		return authorityservice.findAll();
	}
	@RequestMapping(value = "filall", method= RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<AccountSum>> findAlls(){
		List<Authority> sumstaf = authorityservice.getListStaf();
		List<Authority> sumuser = authorityservice.getListUser();
		List<AccountSum> lac = new ArrayList<>();
		AccountSum ac = new AccountSum("Nhân viên",sumstaf.size());
		AccountSum ac1 = new AccountSum("Người dùng",sumuser.size());
		lac.add(ac);
		lac.add(ac1);
		try {
			return new ResponseEntity<Iterable<AccountSum>>(lac, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<AccountSum>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "getstatisticticketandfood", method= RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<Object[]>> getStatisticTicketAndFood(){
			List<Object[]> l = orderservice.listSumRevenues();
		try {
			return new ResponseEntity<Iterable<Object[]>>(l, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<Object[]>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "getumrevenuesbycinema", method= RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<SumRevenuesByCinema>> SumRevenuesByCountry(){
			List<SumRevenuesByCinema> l = ticketservice.SumRevenuesByCinema();
		try {
			return new ResponseEntity<Iterable<SumRevenuesByCinema>>(l, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<SumRevenuesByCinema>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "top5sumticketmovie", method= RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<Top5Movie>> Top5SumTicketMovie(){
			List<Top5Movie> l = ticketservice.Top5SumticketMovie();
		try {
			return new ResponseEntity<Iterable<Top5Movie>>(l, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<Top5Movie>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("getSumTicketByTime")
	public List<Ticket> getSumTicketByTime() throws ParseException{
			Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        String vnp_CreateDate = formatter.format(cld.getTime());
	        Date d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(vnp_CreateDate);
	        Date d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(vnp_CreateDate);
	        d2.setMinutes(d2.getMinutes()-5);
	        List<Ticket> l = ticketservice.getSumTicketByTime(d2,d1);
	       // System.out.println(d2.getHours()+":"+d2.getMinutes()+" | "+d1.getHours()+":"+d1.getMinutes());
		return l;
	}
	
	@GetMapping("/getSumFoodByTime")
	public List<OrderFood> getSumFoodByTime() throws ParseException{
			Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        String vnp_CreateDate = formatter.format(cld.getTime());
	        Date d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(vnp_CreateDate);
	        Date d2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(vnp_CreateDate);
	        d2.setMinutes(d2.getMinutes()-5);
	        List<OrderFood> l = orderfoodservice.getSumFoodByTimes(d2,d1);
	       // System.out.println(d2.getHours()+":"+d2.getMinutes()+" | "+d1.getHours()+":"+d1.getMinutes());
		return l;
	}
	
	@GetMapping("/gettimenow")
	public List<String> getTimeNow() {
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy HH:mm:ss");
        List<String> b = new ArrayList<>();
        b.add(formatter.format(cld.getTime()));
        return b;
	}
	
	@GetMapping("/getdatenow")
	public List<String> getDateNow() {
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        List<String> b = new ArrayList<>();
        b.add(formatter.format(cld.getTime()));
        return b;
	}
	

	@GetMapping("/gettimenows")
	public List<Movie> getTimeNows() {
		List<Movie> b = movieservice.listshow();
        return b;
	}
	@GetMapping("/getSynthetic")
	public List<Long> getSynthetic(){
			Long date = orderservice.getSumRevenuesOfDay();	
			Long month = orderservice.getSumRevenuesOfMonth();
			Long year = orderservice.getSumRevenuesOfYear();
//			System.out.println(date+" | "+month+" | "+year);
			List<Long> list = new ArrayList<>();
			if(date == null) {
				date = (long) 0;
			}
			if(month == null) {
				month = (long) 0;
			}
			if(year == null) {
				year = (long) 0;
			}
			list.add(0, date);
			list.add(1, month);
			list.add(2, year);
		return list;
	}
	
}