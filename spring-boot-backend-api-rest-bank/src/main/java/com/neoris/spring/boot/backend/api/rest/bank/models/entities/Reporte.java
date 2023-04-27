package com.neoris.spring.boot.backend.api.rest.bank.models.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reportes")
public class Reporte implements Serializable {

	private static final long serialVersionUID = 425852877566228331L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Movimiento movimiento;

	private Long clienteId;

	private String fecha;

	public Reporte() {

	}

	public Reporte(Movimiento movimiento, Long clienteId, String fecha) {
		this.movimiento = movimiento;
		this.clienteId = clienteId;
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	public Long getClienteId() {
		return this.movimiento.getCuenta().getCliente().getId();
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getFecha() {
		return this.movimiento.getFecha().toString();
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Reporte [id=" + id + ", movimiento=" + movimiento + ", clienteId=" + clienteId + ", fecha=" + fecha
				+ "]";
	}

}
