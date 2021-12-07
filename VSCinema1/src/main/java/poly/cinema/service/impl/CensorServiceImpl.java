package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.CensorDAO;
import poly.cinema.entity.Censor;
import poly.cinema.service.CensorService;

@Service
public class CensorServiceImpl implements CensorService{

	@Autowired
	CensorDAO censorDAO;

	@Override
	public Censor create(Censor censor) {
		return censorDAO.save(censor);
	}

	@Override
	public List<Censor> findAll() {
		return censorDAO.findAll();
	}
	
}