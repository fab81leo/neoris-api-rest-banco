package com.neoris.spring.boot.backend.api.rest.bank.services;

import java.util.List;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Movimiento;

public interface IMovimientoService {

	List<Movimiento> findAll();
	
	Movimiento findById(Long id);
	
	Movimiento save(Movimiento movimiento);
	
	void delete(Long id);
	
}