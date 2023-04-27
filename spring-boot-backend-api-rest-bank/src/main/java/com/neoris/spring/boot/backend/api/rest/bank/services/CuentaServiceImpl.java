package com.neoris.spring.boot.backend.api.rest.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neoris.spring.boot.backend.api.rest.bank.daos.ICuentaDao;
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cuenta;

@Service
public class CuentaServiceImpl implements ICuentaService {
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> findAll() {
		return (List<Cuenta>) cuentaDao.findAll();		
	}

	@Override
	@Transactional(readOnly = true)
	public Cuenta findById(Long id) {
		return cuentaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta) {
		return cuentaDao.save(cuenta);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		cuentaDao.deleteById(id);		
	}
	
}	