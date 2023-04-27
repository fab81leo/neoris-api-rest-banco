package com.neoris.spring.boot.backend.api.rest.bank.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Reporte;

@Repository
public interface IReporteDao extends JpaRepository<Reporte, Long> {

	List<Reporte> findByFechaAndClienteId(String fecha, Long clienteId);
	
}