package com.jsfspringintegration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Family {

	private Long id;

	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String telefono;

	private String domicilio;
	
	public Family() {
	}

	public Family(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String domicilio) {
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.telefono = telefono;
		this.domicilio = domicilio;
	}

	@Override
	public String toString() {
		return String.format("[nombre: %s, apellidoPaterno: %s, apellidoMaterno: %s, telefono: %s, domicilio: %s]",
				nombre, apellidoPaterno, apellidoMaterno, telefono, domicilio);
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

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String appelidoMaterno) {
		this.apellidoMaterno = appelidoMaterno;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

}
