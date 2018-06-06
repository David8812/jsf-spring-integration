package com.jsfspringintegration.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
@ManagedBean(name = "candidato")
public class Candidato implements Serializable {

	private static final long serialVersionUID = -4019136354848618629L;

	@Id
	private String id;

	@Field
	private String nombre;

	@Field
	private String apellido;

	@Field
	private int sueldoDeseado;

	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaNacimiento;

	@Field
	private List<String> skills;

	@Field
	private Colonia colonia;

	@Transient
	private String coloniaId;

	@Transient
	private String codigoPostal;

	@Transient
	private String ciudad;

	@Field
	private String comentario;

	public Candidato() {
	}

	public Candidato(String id, String nombre, String apellido, int sueldoDeseado, Date fechaNacimiento,
			List<String> skills, Colonia colonia, String comentario) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.sueldoDeseado = sueldoDeseado;
		this.fechaNacimiento = fechaNacimiento;
		this.skills = skills;
		this.colonia = colonia;
		this.comentario = comentario;
	}

	public Candidato copy() {
		return new Candidato(id, nombre, apellido, sueldoDeseado, fechaNacimiento, skills, colonia, comentario);
	}

	public String getColoniaId() {
		return coloniaId;
	}

	public void setColoniaId(String coloniaId) {
		this.coloniaId = coloniaId;
	}

	public String getCodigoPostal1() {
		if (colonia == null) {
			return codigoPostal;
		} else {
			return colonia.getCodigoPostal();
		}
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCiudad() {
		if (colonia == null) {
			return ciudad;
		} else {
			return colonia.getCiudad();
		}
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getSueldoDeseado() {
		return sueldoDeseado;
	}

	public void setSueldoDeseado(int sueldoDeseado) {
		this.sueldoDeseado = sueldoDeseado;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		int offset = fechaNacimiento.getTimezoneOffset();

		System.out.println("Timeoffset: " + offset);

		fechaNacimiento.setMinutes(offset);

		this.fechaNacimiento = fechaNacimiento;
	}

	/*
	 * public void setSkills(List<String> skills) { this.skills = skills;
	 * log.info("Modificando la propiedad skills: "); int i = 0; for (String s :
	 * skills) { String string = s; log.info("valor " + i++ + " :" + string); } }
	 * 
	 * public List<String> getSkills() { return skills; }
	 */

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public Colonia getColonia() {
		return colonia;
	}

	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getComentario() {
		return comentario;
	}

	public String getPostalCode() {
		if (colonia == null) {
			return null;
		} else {
			return colonia.getCodigoPostal();
		}
	}

	public String getCity() {
		if (colonia == null) {
			return null;
		} else {
			return colonia.getCiudad();
		}
	}

	public String getID() {
		if (colonia == null) {
			return null;
		} else {
			return colonia.getId();
		}
	}

	public String getNombreColonia() {
		if (colonia == null) {
			return null;
		} else {
			return colonia.getNombre();
		}
	}

	public void clean() {
		id = nombre = comentario = coloniaId = ciudad = apellido = codigoPostal = null;
		skills = null;
		colonia = new Colonia();
		sueldoDeseado = 0;
		fechaNacimiento = null;
	}

	public String toString() {
		return String.format(
				"[ID:%s, nombre:%s, apellido:%s, sueldo deseado:%s, fecha de nacimiento:%s, Habilidades:%s, Colonia: {codigo postal:%s, nombre: %s, colonia id:%s, ciudad:%s}, comentario:%s]",
				id, nombre, apellido, sueldoDeseado, fechaNacimiento, skills, getCodigoPostal1(), getNombreColonia(),
				getID(), getCiudad(), comentario);
	}
}
