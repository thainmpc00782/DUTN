package poly.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.cinema.entity.Censor;

public interface CensorDAO extends JpaRepository<Censor, Integer>{

}