package com.neoris.spring.boot.backend.api.rest.bank.services;

import java.util.List;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cliente;

public interface IClienteService {

	List<Cliente> findAll();
	
	Cliente findById(Long id);
	
	Cliente save(Cliente cliente);
	
	void delete(Long id);
	
}