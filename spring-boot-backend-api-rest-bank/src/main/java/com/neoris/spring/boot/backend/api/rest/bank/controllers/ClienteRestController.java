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
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Cliente;
import com.neoris.spring.boot.backend.api.rest.bank.services.IClienteService;

/**
 * Ciente Rest Controller
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
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.findById(id);	
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_CONSULTA));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		
		if (cliente == null) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BUSQUEDA_CLIENTE_POR_ID, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		
		Cliente nuevoCliente = null; 
		Map<String, Object> response = new HashMap<>();
		
		try {
			nuevoCliente = clienteService.save(cliente);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_INSERCION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", IMensajes.REGISTRO_CLIENTE_CREADO_CON_EXITO);
		response.put("cliente", nuevoCliente);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
		
		// Cliente atachado al contexto de persistencia
		Cliente clienteActual = clienteService.findById(id);
		
		Cliente clienteActualizado = null;
		Map<String, Object> response = new HashMap<>();
		
		if (clienteActual == null) {
			response.put("mensaje", IMensajesError.ERROR_EDICION_CLIENTE_POR_ID 
					+ String.format(IMensajesError.ERROR_BUSQUEDA_CLIENTE_POR_ID, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			clienteActual.setContrasena(cliente.getContrasena());
			clienteActual.setEstado(cliente.getEstado());	
			clienteActual.setPersona(cliente.getPersona());
			clienteActualizado = clienteService.save(clienteActual);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_ACTUALIZACION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", IMensajes.REGISTRO_CLIENTE_ACTUALIZADO_CON_EXITO);
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = null;
		
		try {
			cliente = clienteService.findById(id);	
			if (cliente == null) {
				response.put("mensaje", String.format(IMensajesError.ERROR_BUSQUEDA_CLIENTE_POR_ID, id));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			clienteService.delete(id);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_ELIMINACION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", IMensajes.REGISTRO_CLIENTE_ELIMINADO_CON_EXITO);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}	
		
}