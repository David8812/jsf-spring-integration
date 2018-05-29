package com.jsfspringintegration.service;

import java.util.List;

import com.jsfspringintegration.model.Candidato;

public interface CandidatoService {
	
	public List<Candidato> findAll();
	
	public Candidato findById(String Id);
	
	public Candidato save(Candidato candidato);
	
	public void deleteById(String Id);
}
