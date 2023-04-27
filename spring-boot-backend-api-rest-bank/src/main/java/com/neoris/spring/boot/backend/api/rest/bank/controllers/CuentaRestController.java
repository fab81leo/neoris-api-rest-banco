package com.neoris.spring.boot.backend.api.rest.bank.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.spring.boot.backend.api.rest.bank.messages.interfaces.IMensajes;
import com.neoris.spring.boot.backend.api.rest.bank.messages.interfaces.IMensajesError;
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cuenta;
import com.neoris.spring.boot.backend.api.rest.bank.services.ICuentaService;

/**
 * Cuenta Rest Controller
 * Se manea @CrossOrigin para dar acceso a este dominio (Frontend) en el caso que se quiera 
 * enviar y recibir datos sobre diferentes tipos de request.
 * [En el caso que trabajaramos con Angular por ejemplo].
 * 
 * @author Fabian Torres
 * @version 1.0.0
 */
@CrossOrigin(origins = {"http://localhost:4200"}, 
			 methods = {RequestMethod.GET,
					 	RequestMethod.POST, 
					 	RequestMethod.PUT, 
					 	RequestMethod.DELETE})
@RestController
@RequestMapping("/banco/api")
public class CuentaRestController {

	@Autowired
	private ICuentaService cuentaService;
	
	@GetMapping("/cuentas")
	public List<Cuenta> index() {
		return cuentaService.findAll();
	}
	
	@GetMapping("/cuentas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Cuenta cuenta = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cuenta = cuentaService.findById(id);	
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_CONSULTA));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}			
		
		if (cuenta == null) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BUSQUEDA_CUENTA_POR_ID, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cuenta>(cuenta, HttpStatus.OK);
		
	}
	
	@PostMapping("/cuentas")
	public ResponseEntity<?> create(@RequestBody Cuenta cuenta) {
		
		Cuenta nuevaCuenta = null; 
		Map<String, Object> response = new HashMap<>();
		
		try {
			nuevaCuenta = cuentaService.save(cuenta);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_INSERCION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", IMensajes.REGISTRO_CUENTA_CREADA_CON_EXITO);
		response.put("cliente", nuevaCuenta);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/cuentas/{id}")
	public ResponseEntity<?> update(@RequestBody Cuenta cuenta, @PathVariable Long id) {
		
		Cuenta cuentaActual = cuentaService.findById(id);
		
		Cuenta cuentaActualizada = null;
		Map<String, Object> response = new HashMap<>();
		
		if (cuentaActual == null) {
			response.put("mensaje", IMensajesError.ERROR_EDICION_CUENTA_POR_ID 
					+ String.format(IMensajesError.ERROR_BUSQUEDA_CUENTA_POR_ID, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			cuentaActual.setNumeroCuenta(cuenta.getNumeroCuenta());
			cuentaActual.setTipo(cuenta.getTipo());
			cuentaActual.setSaldoInicial(cuenta.getSaldoInicial());
			cuentaActual.setEstado(cuenta.getEstado());
			cuentaActual.setCliente(cuenta.getCliente());			
			cuentaActualizada = cuentaService.save(cuentaActual);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_ACTUALIZACION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", IMensajes.REGISTRO_CUENTA_ACTUALIZADA_CON_EXITO);
		response.put("cliente", cuentaActualizada);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/cuentas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		Cuenta cuenta = null;
		
		try {
			cuenta = cuentaService.findById(id);	
			if (cuenta == null) {
				response.put("mensaje", String.format(IMensajesError.ERROR_BUSQUEDA_CUENTA_POR_ID, id));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			cuentaService.delete(id);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_ELIMINACION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", IMensajes.REGISTRO_CUENTA_ELIMINADA_CON_EXITO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
}