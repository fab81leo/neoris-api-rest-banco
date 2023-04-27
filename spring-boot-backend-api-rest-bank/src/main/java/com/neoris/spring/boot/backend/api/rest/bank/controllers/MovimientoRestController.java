package com.neoris.spring.boot.backend.api.rest.bank.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Movimiento;
import com.neoris.spring.boot.backend.api.rest.bank.models.entities.Reporte;
import com.neoris.spring.boot.backend.api.rest.bank.services.IMovimientoService;
import com.neoris.spring.boot.backend.api.rest.bank.services.IReporteService;

/**
 * Movimiento Rest Controller
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
public class MovimientoRestController {

	@Autowired
	private IMovimientoService movimientoService;
	
	@Autowired
	private IReporteService reporteService;
	
	@GetMapping("/movimientos")
	public List<Movimiento> index() {
		return movimientoService.findAll();
	}
	
	@GetMapping("/movimientos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Movimiento movimiento = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			movimiento = movimientoService.findById(id);	
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_CONSULTA));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		
		if (movimiento == null) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BUSQUEDA_MOVIMIENTO_POR_ID, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Movimiento>(movimiento, HttpStatus.OK);
		
	}
	
	@PostMapping("/movimientos")
	public ResponseEntity<?> create(@RequestBody Movimiento movimiento) {
		
		Movimiento nuevoMovimiento = null; 
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (movimiento.getValor() < 0 ) {
				response.put("mensaje", IMensajesError.ERROR_VALOR_DEPOSITO_NO_VALIDO);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			movimiento.setSaldo(movimiento.getValor());
			movimiento.setTipoMovimiento(IMensajes.TIPO_CREDITO
					.concat(" de ").concat(movimiento.getValor().toString()));
			nuevoMovimiento = movimientoService.save(movimiento);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_INSERCION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Registrar Reporte del movimiento		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String fechaMovimiento = dateFormat.format(movimiento.getFecha());
		Reporte reporte = new Reporte(movimiento, 
									  movimiento.getCuenta().getCliente().getId(), 
									  fechaMovimiento);
		
		reporteService.save(reporte);
		
		response.put("mensaje", IMensajes.REGISTRO_MOVIMIENTO_CREADO_CON_EXITO);
		response.put("cliente", nuevoMovimiento);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/movimientos/{id}")
	public ResponseEntity<?> update(@RequestBody Movimiento movimiento, @PathVariable Long id) {
		
		Movimiento movimientoActual = movimientoService.findById(id);
		
		Movimiento movimientoActualizado = null;
		Map<String, Object> response = new HashMap<>();
		
		if (movimientoActual == null) {
			response.put("mensaje", IMensajesError.ERROR_EDICION_MOVIMIENTO_POR_ID 
					+ String.format(IMensajesError.ERROR_BUSQUEDA_MOVIMIENTO_POR_ID, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			double valorAbsoluto = Math.abs(movimiento.getValor());			
			movimientoActual.setValor(valorAbsoluto);
			movimientoActual.setFecha(new Date());
			
			if (movimiento.getValor() >= 0 ) {
				
				movimientoActual.setSaldo(movimientoActual.getSaldo() + movimiento.getValor() );
				movimientoActual.setTipoMovimiento(IMensajes.TIPO_CREDITO
						.concat(" de ").concat(movimiento.getValor().toString()));
				
			} else {
				Double saldoFinal = movimientoActual.getSaldo() - valorAbsoluto;
				if (movimientoActual.getSaldo() == 0) {
					response.put("mensaje", IMensajesError.ERROR_SALDO_NO_DISPONIBLE);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
				if (saldoFinal < 0) {
					response.put("mensaje", IMensajesError.ERROR_RETIRO_SUPERA_SALDO_ACTUAL);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
				movimientoActual.setSaldo(saldoFinal);				
				movimientoActual.setTipoMovimiento(IMensajes.TIPO_DEBITO
						.concat(" de ").concat( String.valueOf(valorAbsoluto)));
			}
						
			movimientoActual.setCuenta(movimiento.getCuenta());
			
			movimientoActualizado = movimientoService.save(movimientoActual);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_ACTUALIZACION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Registrar Reporte del movimiento		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String fechaMovimiento = dateFormat.format(movimientoActual.getFecha());
		Reporte reporte = new Reporte(movimientoActual, 
									  movimientoActual.getCuenta().getCliente().getId(), 
									  fechaMovimiento);
				
		reporteService.save(reporte);
		
		response.put("mensaje", IMensajes.REGISTRO_MOVIMIENTO_ACTUALIZADO_CON_EXITO);
		response.put("cliente", movimientoActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/movimientos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		Movimiento movimiento = null;
		try {
			movimiento = movimientoService.findById(id);	
			if (movimiento == null) {
				response.put("mensaje", String.format(IMensajesError.ERROR_BUSQUEDA_MOVIMIENTO_POR_ID, id));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			movimientoService.delete(id);
		} catch(DataAccessException e) {
			response.put("mensaje", String.format(IMensajesError.ERROR_BASE_DE_DATOS, IMensajesError.ERROR_BD_ELIMINACION));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", IMensajes.REGISTRO_MOVIMIENTO_ELIMINADO_CON_EXITO);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
		
}