package com.neoris.spring.boot.backend.api.rest.bank.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Movimiento;

@Repository
public interface IMovimientoDao extends CrudRepository<Movimiento, Long> {

}