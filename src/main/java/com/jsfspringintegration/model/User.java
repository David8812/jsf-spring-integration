package com.jsfspringintegration.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@ManagedBean(name = "usuario")
public class User implements Serializable {
	private static final long serialVersionUID = -3009157732242241606L;

	@Id
	private String id;

	@Field(value = "user_name")
	private String userName;

	@Field
	private String password;

	@Transient
	private String confirmPassword;

	@Field
	private String roll;

	public User() {
	}

	public User(String userName, String password, String roll) {
		this.userName = userName;
		this.password = password;
		this.roll = roll;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getRoll() {
		return roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		User u = (User) o;
		return this.userName.equals(u.getUserName()) && this.password.equals(u.getPassword());
	}

	@Override
	public String toString() {
		return String.format("[Id: %d, Nombre Usuario: %s, Password: %s, Roll:%s]", id, userName, password, roll);
	}

}
