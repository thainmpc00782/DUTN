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



import poly.cinema.entity.Sale;
import poly.cinema.service.SaleService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/sale")
public class SaleRestController {

	@Autowired
	SaleService saleService;
	
	@GetMapping()
	public List<Sale> findAll() {
		return saleService.findAll();
	}
	
	@GetMapping("{id}")
	public List<Sale> getOne(@PathVariable("id") Integer id) {
		return saleService.findByFoodId(id);
	}
	
	@PostMapping()
	public Sale save(@RequestBody Sale sale) {
		return saleService.create(sale);
	}
	 
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		saleService.deleteById(id);
	}
	
	@PutMapping("{id}")
	public Sale put(@PathVariable("id") Integer id, @RequestBody Sale sale) {
		return saleService.update(sale);
	}
}
