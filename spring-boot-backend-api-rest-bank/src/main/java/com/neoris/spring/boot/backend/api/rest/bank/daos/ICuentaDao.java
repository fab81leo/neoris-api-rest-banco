package com.neoris.spring.boot.backend.api.rest.bank.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cuenta;

@Repository
public interface ICuentaDao extends CrudRepository<Cuenta, Long> {

}