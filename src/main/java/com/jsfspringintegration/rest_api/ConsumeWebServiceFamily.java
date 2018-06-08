package com.jsfspringintegration.rest_api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.jsfspringintegration.model.Family;

@ManagedBean
@ViewScoped
public class ConsumeWebServiceFamily {

	private boolean defaultSelection = false;

	private boolean radioButtonTextDisable = false;

	private Integer id;

	private List<Family> families = new ArrayList<>();

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

	public void handleRadioChange() {
		radioButtonTextDisable = !radioButtonTextDisable;
	}

	public void consumeRestFamily() {

		System.out.println(id);
		families.clear();

		try {
			HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().nonPreemptive()
					.credentials("David", "d").build();

			System.out.println("Http feature created");

			ClientConfig clientConfig = new ClientConfig();
			clientConfig.register(feature);

			System.out.println("Client registered...");

			Client cliente = ClientBuilder.newClient(clientConfig);

			System.out.println("Client built...");

			WebTarget target = cliente.target("http://localhost:8181/web-app/api_rest/").path("/family");
			
			if (radioButtonTextDisable) {
				List<Family> list = target.request(MediaType.APPLICATION_JSON).get(List.class);
				System.out.println("List family found=>" + list);
				
				families.addAll(list);
			} else {
				Family f = target.path("/" + id).request(MediaType.APPLICATION_JSON).get(Family.class);
				System.out.println("Family found=>" + f);
				
				families.add(f);
			}
			
			if(families.isEmpty())
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
	
	private void createNotFoundInfoMessage() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		String summary = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['notFoundError']}",
				String.class);
		String detail = ctx.getApplication().evaluateExpressionGet(ctx, "#{msgs['notFoundErrorFamily']}",
				String.class);
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}
}
