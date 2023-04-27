package com.neoris.spring.boot.backend.api.rest.bank.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
public class Persona implements Serializable {

	private static final long serialVersionUID = -5397513560617705092L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(nullable = false)
	private Character genero;

	@Column(nullable = false, length = 3)
	private Byte edad;

	@Column(nullable = false, length = 12)
	private String identificacion;

	@Column(nullable = true, length = 200)
	private String direccion;

	@Column(nullable = true, length = 15)
	private String telefono;

	public Persona() {

	}

	public Persona(String nombre, Character genero, Byte edad, String identificacion, String direccion,
			String telefono) {
		this.nombre = nombre;
		this.genero = genero;
		this.edad = edad;
		this.identificacion = identificacion;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Character getGenero() {
		return genero;
	}

	public void setGenero(Character genero) {
		this.genero = genero;
	}

	public Byte getEdad() {
		return edad;
	}

	public void setEdad(Byte edad) {
		this.edad = edad;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", edad=" + edad
				+ ", identificacion=" + identificacion + ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}

}