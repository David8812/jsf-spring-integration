package com.jsfspringintegration.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;

import com.jsfspringintegration.model.Colonia;
import com.jsfspringintegration.service.ColoniaService;

@ManagedBean
@ViewScoped
public class ColoniaBean {

	private Colonia colonia = new Colonia();

	@ManagedProperty("#{coloniaService}")
	private ColoniaService coloniaService;

	private List<SelectItem> colonias;

	public ColoniaBean() {
	}

	public void codigoPostalListener(AjaxBehaviorEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		
		UIInput input = (UIInput) event.getSource();
		
		UIComponent parent = input.getParent();
		
		while(!(parent instanceof UIForm)) {
			parent = parent.getParent();
		}
		
		
		String formId = parent.getClientId();
		
		System.out.println("Form ID=>" + formId);
		
		String clientId = input.getClientId();
		System.out.println("ID del elemento=>" + clientId);
		
		String newCodigoPostal = (String) input.getValue();
		
		System.out.println("Nuevo codigo postal: " + newCodigoPostal);
		
		int idx = clientId.lastIndexOf(":");
		String part1 = clientId.substring(0, idx + 1);
		String part2 = "coloniaId";
		String newId = formId + ":" + part1 + part2;
		
		System.out.println("ID to modify colony name=>" + newId);
		
		PrimeFaces.current().resetInputs(newId);
		UIInput coloniaInputText = (UIInput) uiViewRoot.findComponent(newId);
		
		Colonia c = coloniaService.findByCodigoPostal(newCodigoPostal);

		if (c == null) {
			c = new Colonia();
			c.setId("0");
			c.setCiudad("");
		}
		System.out.println("Colonia con nuevo codigo postal: " + c);

		String coloniaId = c.getId() + "-" + c.getNombre();
		coloniaInputText.setValue(coloniaId);
		coloniaInputText.setSubmittedValue(coloniaId);

		part2 = "ciudad";
		newId = formId + ":" + part1 + part2;
		
		System.out.println("ID to modify city name=>" + newId);
		
		PrimeFaces.current().resetInputs(newId);
		UIInput ciudadInputText = (UIInput) uiViewRoot.findComponent(newId);

		String ciudad = c.getCiudad();
		ciudadInputText.setValue(ciudad);
		ciudadInputText.setSubmittedValue(ciudad);
		
		facesContext.renderResponse();
	}

	public void crearNuevo() {
		System.out.println("Entrando a guardar colonia: " + colonia.toString());

		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['colonyAddedCorrectly']}",
				String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['colonyAddedCorrectlyDetail']}",
				String.class);
		ctx.addMessage("addedCorrectly", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));

		coloniaService.save(colonia);
		
		colonias.add(new SelectItem(colonia.getId() + "-" + colonia.getNombre(), colonia.getNombre()));

		colonia = new Colonia();
		
		PrimeFaces.current().ajax().update("form:display");
	}

	private void fillColonias() {
		System.out.println("Entrando a listar colonias: servicio=>" + coloniaService);
		colonias = new ArrayList<>();
		List<Colonia> list = coloniaService.findAll();
		System.out.println(list);
		for (Colonia colonia : list) {
			colonias.add(new SelectItem(colonia.getId() + "-" + colonia.getNombre(), colonia.getNombre()));
		}
	}

	public List<SelectItem> getColonias() {
		fillColonias();
		return colonias;
	}

	public void setColonias(List<SelectItem> colonias) {
		this.colonias = colonias;
	}

	public ColoniaService getColoniaService() {
		return coloniaService;
	}

	public void setColoniaService(ColoniaService coloniaService) {
		this.coloniaService = coloniaService;
	}

	public Colonia getColonia() {
		return colonia;
	}

	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}

}
