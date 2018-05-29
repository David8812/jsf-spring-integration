package com.jsfspringintegration.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "showHideController")
@SessionScoped
public class ShowHideController {

	private boolean comentarioEnviado = true;

	public boolean isComentarioEnviado() {
		return comentarioEnviado;
	}

	public void setComentarioEnviado(boolean comentarioEnviado) {
		this.comentarioEnviado = comentarioEnviado;
	}

	public void ocultarComentario() {
		this.comentarioEnviado = !this.comentarioEnviado;
	}
}
