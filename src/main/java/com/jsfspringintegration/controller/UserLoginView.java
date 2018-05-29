package com.jsfspringintegration.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "userLoginView")
@RequestScoped
public class UserLoginView {

	private String username;

	private String password;

	public UserLoginView() {
	}

	@PostConstruct
	public void init() {
		System.out.println("Iniciando construccion userLoginView");
		this.username = "";
		this.password = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		System.out.println("beans.backing.UserLoginView.login()->username: " + username + ", password: " + password);
		FacesMessage message = null;
		FacesContext context = FacesContext.getCurrentInstance();
		if (username != null && username.equals("admin") && password != null && password.equals("admin")) {
			String msg = context.getApplication().evaluateExpressionGet(context, "#{msgs['logInSucces']} ", String.class);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, username);
			context.addMessage("loggAction", message);
			System.out.println("log in success");
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("logged", true);
			// return "indexLogged?faces-redirect=true";
		} else {
			String msg = context.getApplication().evaluateExpressionGet(context, "#{msgs['logInError']}", String.class);
			String detail = context.getApplication().evaluateExpressionGet(context, "#{msgs['logInErrorDetail']}", String.class);
			
			System.out.println("ok es por aqui");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, detail);
			System.out.println("mensaje instanciado correctamente");
			context.addMessage("loggAction", message);
			System.out.println("Faces Context obtenido correctamente");
		}
		return "index";
		// PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
	}
	
	public String logout() {
		System.out.println("Cerrando sesión");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().removeAttribute("logged");
		return "index";
	}
}
