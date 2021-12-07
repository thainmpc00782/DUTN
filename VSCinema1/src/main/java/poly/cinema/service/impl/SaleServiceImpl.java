package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import poly.cinema.dao.SaleDAO;
import poly.cinema.entity.Sale;
import poly.cinema.service.SaleService;

@Service
public class SaleServiceImpl implements SaleService{

	@Autowired
	SaleDAO dao;
	

	@Override
	public List<Sale> findAll() {
		return dao.findAll();
	}
	
	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
	
	@Override
	public Sale create(Sale sale) {
		return dao.save(sale);
	}
	
	@Override
	public Sale update(Sale sale) {
		return dao.save(sale);
	}
	

	@Override
	public Sale findById(Integer id) {

		return dao.findById(id).get();
	}
	
	@Override
	public List<Sale> findByFoodId(Integer id) {
		// TODO Auto-generated method stub
		return dao.findByFoodID(id);
	}
}