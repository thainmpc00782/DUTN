
package poly.cinema.service;

import java.util.List;

import poly.cinema.entity.Censor;

public interface CensorService {

	Censor create(Censor censor);

	List<Censor> findAll();

}
