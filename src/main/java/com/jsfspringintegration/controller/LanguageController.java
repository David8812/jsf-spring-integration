package com.jsfspringintegration.controller;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ManagedBean(name = "languageController")
@SessionScoped
public class LanguageController {

    private Locale locale;

    @PostConstruct
    public void init() {
    	System.out.println("Iniciando construccion LanguageController");
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();

        Cookie cookie = findLanguageCookie(request, "language");

        if (cookie == null) {
            System.out.println("No se encontro cookie de lenguaje, creando cookie con lenguaje local...");
            locale = ctx.getExternalContext().getRequestLocale();
            cookie = new Cookie("language", getLanguage());
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(-1);

            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            response.addCookie(cookie);
        } else {
            String language = cookie.getValue();
            System.out.println("Se encontro cookie de lenguaje, no es neceario crear una nueva, language: " + language);
            locale = new Locale(language);
        }
    }

    public Locale getLocale() {
    	if(locale == null) {
    		System.out.println("Locale es null, llamando al metodo init()");
    		init();
    	}
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    private void setLanguage(String language) {
    	locale = new Locale(language);
        FacesContext ctx = FacesContext.getCurrentInstance();
    	
    	//FacesContextHelper.limpiarImmediateFacesMessages(ctx);
   
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        Cookie cookie = findLanguageCookie(request, "language");

        if (cookie == null) {
            System.out.println("Cookie no encontrada en cambio de idioma, creando nueva");
            cookie = new Cookie(language, getLanguage());
        } else
            System.out.println("Cookie encontrada en cambio de idioma, name: " + cookie.getName() + " ,value: " + cookie.getValue());

        cookie.setValue(language);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(-1);

        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        response.addCookie(cookie);

        ctx.getViewRoot().setLocale(locale);
    }

    public void changeLanguageToEnglish() {
        setLanguage("en");
    }

    public void changeLanguageToSpanish() {
        setLanguage("es");
    }

    private Cookie findLanguageCookie(HttpServletRequest request, String name) {
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (Cookie userCookie : userCookies) {
                if (userCookie.getName().equals(name)) {
                    return userCookie;
                }
            }
        }
        return null;
    }
}
