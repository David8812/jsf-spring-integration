package com.jsfspringintegration.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.PrimeFaces;

import com.jsfspringintegration.model.Candidato;
import com.jsfspringintegration.model.Colonia;
import com.jsfspringintegration.service.CandidatoService;

@ManagedBean
@ViewScoped
public class CandidatoBean {

	private Candidato candidato = new Candidato();

	@ManagedProperty("#{candidatoService}")
	private CandidatoService candidatoService;

	private List<Candidato> candidatos;

	public CandidatoBean() {
	}

	public void showDialog() {
		System.out.println("showDialog() method called");

		PrimeFaces.current().ajax().update("form:display");
		PrimeFaces.current().executeScript("PF('dialog').show()");
	}
	
	public void restartSelectedCandidate() {
		this.candidato = new Candidato();
	}

	public String crearNuevo() {
		System.out.println("Insertando candidato ANTES: " + candidato);

		String ID = candidato.getColoniaId();
		String[] tmp = ID.split("[-]");
		System.out.println("ID: " + tmp[0] + " Nombre: " + tmp[1]);

		Colonia c = new Colonia();
		c.setId(tmp[0]);
		c.setNombre(tmp[1]);
		c.setCodigoPostal(candidato.getCodigoPostal());
		c.setCiudad(candidato.getCiudad());
		candidato.setColonia(c);

		System.out.println("Insertando candidato DESPUES: " + candidato);

		candidatoService.save(candidato);

		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['candidateAddedCorrectly']}",
				String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['candidateAddedCorrectlyDetail']}",
				String.class);
		ctx.addMessage("addedCorrectly", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));

		candidato.clean();

		return "addCandidate";
	}
	
	private void fillCandidatos() {
		candidatos = candidatoService.findAll();
	}

	public CandidatoService getCandidatoService() {
		return candidatoService;
	}

	public void setCandidatoService(CandidatoService candidatoService) {
		this.candidatoService = candidatoService;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public List<Candidato> getCandidatos() {
		fillCandidatos();
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}
	
}
