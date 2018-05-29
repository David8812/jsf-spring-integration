package com.jsfspringintegration.service;

import java.util.List;

import com.jsfspringintegration.model.Colonia;

public interface ColoniaService {

	public List<Colonia> findAll();

	public Colonia findById(String Id);

	public Colonia save(Colonia colonia);

	public void deleteById(String Id);

	public Colonia findByCodigoPostal(String codigoPostal);
}
