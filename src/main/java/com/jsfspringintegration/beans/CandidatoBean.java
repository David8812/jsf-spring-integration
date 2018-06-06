package com.jsfspringintegration.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.jsfspringintegration.model.Candidato;
import com.jsfspringintegration.model.Colonia;
import com.jsfspringintegration.service.CandidatoService;
import com.jsfspringintegration.service.ColoniaService;

@ManagedBean
@ViewScoped
public class CandidatoBean {

	private Candidato candidato = new Candidato();
	
	@ManagedProperty("#{candidatoService}")
	private CandidatoService candidatoService;

	@ManagedProperty("#{coloniaService}")
	private ColoniaService coloniaService;

	private List<Candidato> candidatos;

	public CandidatoBean() {
	}

	public void showDialog() {
		System.out.println("showDialog() method called");

		PrimeFaces.current().ajax().update("form:display");
		PrimeFaces.current().executeScript("PF('dialog').show()");
	}

	public void showCandidatoDialog() {
		System.out.println("showCandidatoDialog() method called");

		restartSelectedCandidate();

		PrimeFaces.current().ajax().update("vacanteForm:display");
		PrimeFaces.current().executeScript("PF('candiatoDialogo').show()");
	}

	private Thread findThreadByName(String n) {
		for (Thread t : Thread.getAllStackTraces().keySet()) {
			String name = t.getName();
			if (name.equals(n))
				return t;
		}
		return null;
	}

	public void updateComponent() {
		System.out.println("updateComponent() method call");
		Thread t = findThreadByName("fillCandidatosList");
		while (t != null && !t.isInterrupted())
			;

		if (candidatos == null || candidatos.isEmpty())
			fillCandidatos();
	}

	private void waitTwoSeconds() {
		System.out.println("Method wait called");

		try {
			Thread.sleep(2000);
			System.out.println("finished");
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void restartSelectedCandidate() {
		this.candidato = new Candidato();
	}

	public void editListener(RowEditEvent event) {
		Candidato c = (Candidato) event.getObject();
		System.out.println("Editando candidato: " + c);
		candidatoService.save(c);

		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['candidateAddedCorrectly']}",
				String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['candidateEditedCorrectlyDetail']}",
				String.class);
		ctx.addMessage("globalMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	public void deleteCandidate() {
		System.out.println("Eliminando candidato: " + candidato);

		candidatoService.deleteById(candidato.getId());

		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['candidateAddedCorrectly']}",
				String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['candidateDeletedCorrectlyDetail']}",
				String.class);
		ctx.addMessage("globalMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));

		fillCandidatos();

		restartSelectedCandidate();
	}

	public void crearNuevo() {
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
		ctx.addMessage("globalMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));

		fillCandidatos();

		candidato.clean();
	}
	
	public void forceReloadListData() {
		System.out.println("refilling list...");
		fillCandidatos();
	}

	public void updateColonyDataOnList(AjaxBehaviorEvent event) {
		UIInput input = (UIInput) event.getSource();
		String id = input.getClientId();
		int st = id.indexOf(":");
		int ed = id.indexOf(":", st + 1);
		int pos = Integer.parseInt(id.substring(st + 1, ed));

		String codigoPostal = (String) input.getValue();
		Colonia c = coloniaService.findByCodigoPostal(codigoPostal);
		
		System.out.println("Colony values=>" + c);
		
		if(c == null) {
			c = new Colonia();
			c.setCodigoPostal(codigoPostal);
		}

		candidatos.get(pos).setColonia(c);

		System.out.println("ID=>" + id);
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
		if (candidatos == null || candidatos.isEmpty()) {
			if (null == findThreadByName("fillCandidatosList")) {
				System.out.println("Starting thread...");
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						waitTwoSeconds();
					}
				}, "fillCandidatosList");
				t.start();
			}
		}
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public ColoniaService getColoniaService() {
		return coloniaService;
	}

	public void setColoniaService(ColoniaService coloniaService) {
		this.coloniaService = coloniaService;
	}

}
