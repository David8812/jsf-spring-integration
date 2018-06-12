package com.jsfspringintegration.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@ManagedBean(name = "userLoginView")
@ViewScoped
public class UserLoginView {

	private String username;

	private String password;
	
	@ManagedProperty("#{authenticationManager}")
	private AuthenticationManager authenticationManager;

	public UserLoginView() {
	}

	@PostConstruct
	public void init() {
		System.out.println("Iniciando construccion userLoginView");
		this.username = "";
		this.password = "";
	}
	
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
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
	
	public void test() {
		System.out.println("ok");
	}

	public String login() {
		System.out.println("UserLoginView.login()->username: " + username + ", password: " + password);
		
		//System.out.println("AM=>" + getAuthenticationManager());
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		FacesMessage message = null;
		
		try {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			final Authentication auth = authenticationManager.authenticate(authRequest);
			
			request.getSession().setMaxInactiveInterval(10 * 60); //expire session after 10 minutes with no requests from client
			
			if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				request.getSession().setAttribute("auth", auth);
				
				String msg = context.getApplication().evaluateExpressionGet(context, "#{msgs['logInSucces']} ", String.class);
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, username);
				context.addMessage("loggAction", message);
				
				System.out.println("log in success");
			}
		} catch (BadCredentialsException e) {			
			String msg = context.getApplication().evaluateExpressionGet(context, "#{msgs['logInError']}", String.class);
			String detail = context.getApplication().evaluateExpressionGet(context, "#{msgs['logInErrorDetail']}", String.class);
			
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, detail);
			context.addMessage("loggAction", message);
			
			System.out.println(e);
		}
		return "index";
		// PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
	}
	
	public String logout() {
		System.out.println("Cerrando sesión");
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			request.getSession().removeAttribute("usuario");
			request.getSession().removeAttribute("auth");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		request.getSession().removeAttribute("auth");
		return "index?faces-redirect=true";
	}
}
