package poly.cinema.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetNewTimeRestController {
	@GetMapping("/rest/getnewdate")
	public String getNewDate() throws ParseException {
		Date d = new Date(); 
		SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd"); 
	//	Date date = dt.parse(d.getYear()+d.getMonth()+d.getDate()+""); 
	//	System.out.println("Năm"+d.getYear());
		return "";
	}
}