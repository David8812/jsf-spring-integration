package com.jsfspringintegration.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.jsfspringintegration.model.Family;

@ManagedBean
@ViewScoped
public class ConsumeWebServiceFamily {

	private boolean defaultSelection = false;

	private boolean radioButtonTextDisable = false;

	private Integer id;

	private List<Family> families = new ArrayList<>();

	private Family familia = new Family();

	public Family getFamilia() {
		return familia;
	}

	public void setFamilia(Family familia) {
		this.familia = familia;
	}

	public List<Family> getFamilies() {
		return families;
	}

	public void setFamilies(List<Family> families) {
		this.families = families;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isRadioButtonTextDisable() {
		return radioButtonTextDisable;
	}

	public void setRadioButtonTextDisable(boolean radioButtonTextDisable) {
		this.radioButtonTextDisable = radioButtonTextDisable;
	}

	public boolean isDefaultSelection() {
		return defaultSelection;
	}

	public void setDefaultSelection(boolean defaultSelection) {
		this.defaultSelection = defaultSelection;
	}

	public void editListener(RowEditEvent event) {
		System.out.println("edit event...");
		Family familia = (Family) event.getObject();
		
		System.out.println(familia);
		
		WebTarget target = getRestWebServiceConnection("David", "d", "http://localhost:8181/web-app/api_rest/", "family/update");
		
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(familia, MediaType.APPLICATION_JSON));

		int responseStatus = response.getStatus();
		StatusType type = response.getStatusInfo();
		System.out.println("Respuesta=>" + type);

		if (responseStatus == 201) {
			createOkInfoMessage();

			//Family f = response.readEntity(Family.class);
			// List<Family> list = findAllFamilies(getRestWebServiceConnection("David", "d",
			// "http://localhost:8181/web-app/api_rest/", "family"));
			// families.clear();
			//families.add(f);
		} else
			creatUnkonwnError(type);

		//familia = new Family();
	}

	public void handleRadioChange() {
		radioButtonTextDisable = !radioButtonTextDisable;
	}

	public void showCrearNuevoDialogo() {
		System.out.println("showCrearNuevoDialogo() method called");

		PrimeFaces.current().ajax().update("vacanteForm:display");
		PrimeFaces.current().executeScript("PF('familiaDialogo').show()");
	}

	private WebTarget getRestWebServiceConnection(String username, String password, String url, String path) {

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().nonPreemptive()
				.credentials(username, password).build();

		System.out.println("Http feature created");

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(feature);

		System.out.println("Client registered...");

		Client cliente = ClientBuilder.newClient(clientConfig);

		System.out.println("Client built...");

		WebTarget target = cliente.target(url).path("/" + path);

		return target;
	}

	public void crearNuevo() {
		System.out.println("guardando persona=>" + familia);

		WebTarget target = getRestWebServiceConnection("David", "d", "http://localhost:8181/web-app/api_rest/",
				"family/create");

		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(familia, MediaType.APPLICATION_JSON));

		int responseStatus = response.getStatus();
		StatusType type = response.getStatusInfo();
		System.out.println("Respuesta=>" + type);

		if (responseStatus == 201) {
			createOkInfoMessage();

			Family f = response.readEntity(Family.class);
			families.add(f);
		} else
			creatUnkonwnError(type);

		familia = new Family();
	}
	
	public void deleteCandidate() {
		System.out.println("deleting... " + familia);
		
		WebTarget target = getRestWebServiceConnection("David", "d", "http://localhost:8181/web-app/api_rest/", "family");
		
		Invocation.Builder invocationBuilder = target.path("/" + familia.getId()).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();
		
		int responseStatus = response.getStatus();
		StatusType type = response.getStatusInfo();
		System.out.println("Respuesta=>" + type);
		
		if (responseStatus == 201) {
			createOkDeletedInfoMessage();
			
			families.remove(familia);
		} else
			creatUnkonwnError(type);
	}

	private List<Family> findAllFamilies(WebTarget target) {
		return target.request(MediaType.APPLICATION_JSON).get(Response.class).readEntity(new GenericType<List<Family>>() {});
	}

	private Family findFamilyById(WebTarget target) {
		return target.path("/" + id).request(MediaType.APPLICATION_JSON).get(Family.class);
	}

	public void consumeRestFamily() {

		System.out.println(id);
		families.clear();

		try {
			WebTarget target = getRestWebServiceConnection("David", "d", "http://localhost:8181/web-app/api_rest/", "family");

			if (radioButtonTextDisable) {
				List<Family> list = findAllFamilies(target);
				families.addAll(list);
			} else {
				Family f = findFamilyById(target);
				families.add(f);
			}

			if (families.isEmpty())
				createNotFoundInfoMessage();

		} catch (NotAuthorizedException e) {
			System.out.println("No autorizado: Usuario o contraseña invalida");
			System.out.println("Mensaje: " + e.getMessage());
		} catch (NotFoundException e) {
			System.out.println("No encontrado: id desconocido");
			System.out.println("Mensaje: " + e.getMessage());

			createNotFoundInfoMessage();
		}
	}

	private void createOkInfoMessage() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['colonyAddedCorrectly']}",
				String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['correctPost']}", String.class);
		ctx.addMessage("restServiceGlobalErrors", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}
	
	private void createOkDeletedInfoMessage() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['colonyAddedCorrectly']}",
				String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['correctDelete']}", String.class);
		ctx.addMessage("restServiceGlobalErrors", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	private void creatUnkonwnError(StatusType type) {
		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "ERROR " + type.getStatusCode(), String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, type.getReasonPhrase(), String.class);
		ctx.addMessage("restServiceGlobalErrors", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	private void createNotFoundInfoMessage() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['notFoundError']}", String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['notFoundErrorFamily']}", String.class);
		ctx.addMessage("restServiceGlobalErrors", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}
}
