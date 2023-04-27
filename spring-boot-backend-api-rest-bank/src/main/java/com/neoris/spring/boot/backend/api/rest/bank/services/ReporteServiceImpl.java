package com.neoris.spring.boot.backend.api.rest.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoris.spring.boot.backend.api.rest.bank.daos.IReporteDao;
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Reporte;

@Service
public class ReporteServiceImpl implements IReporteService {
	
	@Autowired
	private IReporteDao reporteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Reporte> findAll() {
		return (List<Reporte>) reporteDao.findAll();		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reporte> findByFechaAndClienteId(String fecha, Long clienteId) {
		return reporteDao.findByFechaAndClienteId(fecha, clienteId);
	}

	@Override
	@Transactional
	public Reporte save(Reporte reporte) {
		return reporteDao.save(reporte);
	}

}