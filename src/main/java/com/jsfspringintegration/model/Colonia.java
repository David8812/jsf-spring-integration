package com.jsfspringintegration.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@ManagedBean(name = "colonia")
public class Colonia implements Serializable {

	private static final long serialVersionUID = -5131653667519636672L;

	@Id
	private String id;

	@Field
	private String nombre;

	@Field
	private String ciudad;

	@Field
	private String codigoPostal;

	public Colonia() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return String.format("[ID: %s, Nombre:%s, Ciudad:%s, Codigo Postal:%s]", id, nombre, ciudad, codigoPostal);
	}
}
