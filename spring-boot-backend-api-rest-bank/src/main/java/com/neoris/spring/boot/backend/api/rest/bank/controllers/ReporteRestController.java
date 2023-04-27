package com.neoris.spring.boot.backend.api.rest.bank.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Reporte;
import com.neoris.spring.boot.backend.api.rest.bank.services.IReporteService;

/**
 * Reporte Rest Controller
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
public class ReporteRestController {

	@Autowired
	private IReporteService reporteService;
	
	@GetMapping("/reportes")
	public List<Reporte> index() {
		return reporteService.findAll();
	}
		
	@GetMapping("/reportes/{fecha}/{clienteId}")
	public List<Reporte> show(@PathVariable String fecha, @PathVariable Long clienteId) {
		return reporteService.findByFechaAndClienteId(fecha, clienteId);
	}
	
}