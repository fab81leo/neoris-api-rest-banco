package com.neoris.spring.boot.backend.api.rest.bank.services;

import java.util.List;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cuenta;

public interface ICuentaService {

	List<Cuenta> findAll();
	
	Cuenta findById(Long id);
	
	Cuenta save(Cuenta cuenta);
	
	void delete(Long id);
	
}