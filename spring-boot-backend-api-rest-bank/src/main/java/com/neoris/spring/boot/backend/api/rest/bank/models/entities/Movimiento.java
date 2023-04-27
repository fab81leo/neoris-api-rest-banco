package com.neoris.spring.boot.backend.api.rest.bank.models.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "movimientos")
public class Movimiento implements Serializable {

	private static final long serialVersionUID = 2031528863763785689L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(nullable = true, length = 90)
	private String tipoMovimiento;

	@Column(nullable = false)
	private Double valor;

	@Column(nullable = true)
	private Double saldo;

	@ManyToOne
	private Cuenta cuenta;

	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}

	public Movimiento() {

	}

	public Movimiento(Date fecha, String tipoMovimiento, Double valor, Double saldo) {
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;
		this.valor = valor;
		this.saldo = saldo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", fecha=" + fecha + ", tipoMovimiento=" + tipoMovimiento + ", valor=" + valor
				+ ", saldo=" + saldo + ", cuenta=" + cuenta + "]";
	}

}