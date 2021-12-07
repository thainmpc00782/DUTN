package poly.cinema.service;

import java.util.List;

import poly.cinema.entity.PriceShow;

public interface PriceShowService {
	List<PriceShow> getAll();

	PriceShow create(PriceShow priceShow);

	PriceShow update(PriceShow priceShow);
	}