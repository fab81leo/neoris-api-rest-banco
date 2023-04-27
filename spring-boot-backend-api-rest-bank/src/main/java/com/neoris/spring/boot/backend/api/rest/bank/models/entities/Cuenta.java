package com.neoris.spring.boot.backend.api.rest.bank.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas")
public class Cuenta implements Serializable {

	private static final long serialVersionUID = -3059970559778226531L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 10)
	private Integer numeroCuenta;

	@Column(nullable = false, length = 9)
	private String tipo;

	@Column(nullable = false)
	private Double saldoInicial;

	@Column(nullable = false, length = 5)
	private String estado;

	@ManyToOne
	private Cliente cliente;

	public Cuenta() {

	}

	public Cuenta(Integer numeroCuenta, String tipo, Double saldoInicial, String estado) {
		this.numeroCuenta = numeroCuenta;
		this.tipo = tipo;
		this.saldoInicial = saldoInicial;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(Integer numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", numeroCuenta=" + numeroCuenta + ", tipo=" + tipo + ", saldoInicial="
				+ saldoInicial + ", estado=" + estado + ", cliente=" + cliente + "]";
	}

}