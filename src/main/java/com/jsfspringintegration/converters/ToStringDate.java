package com.jsfspringintegration.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("beans.converters.ToStringDate")
public class ToStringDate implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			System.out.println("Parseando fecha a formato");
			return format.parse(value);
		} catch (ParseException e) {
			System.out.println("Algo ha sucedido y no se ha podido parsear la fecha");
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date) value;
		System.out.println("Formateando fecha...");
		return format.format(date);
	}

}
