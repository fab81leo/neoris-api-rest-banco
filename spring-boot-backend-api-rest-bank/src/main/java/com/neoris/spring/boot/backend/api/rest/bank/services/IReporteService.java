package com.neoris.spring.boot.backend.api.rest.bank.services;

import java.util.List;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Reporte;

public interface IReporteService {

	List<Reporte> findAll();
	
	List<Reporte> findByFechaAndClienteId(String fecha, Long clienteId);
	
	Reporte save(Reporte reporte);
	
}