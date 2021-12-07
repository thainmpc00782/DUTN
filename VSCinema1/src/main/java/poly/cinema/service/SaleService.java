package poly.cinema.service;

import java.util.List;


import poly.cinema.entity.Sale;

public interface SaleService {

	List<Sale> findAll();

	void deleteById(Integer id);

	Sale create(Sale sale);

	Sale update(Sale sale);

	Sale findById(Integer id);

	List<Sale> findByFoodId(Integer id);


}