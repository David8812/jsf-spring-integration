package com.jsfspringintegration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsfspringintegration.model.Candidato;
import com.jsfspringintegration.repository.CandidatoRepository;

@Service("candidatoService")
public class CandidatoServiceImpl implements CandidatoService {

	@Autowired
	CandidatoRepository candidatoRepository;

	@Override
	public List<Candidato> findAll() {
		return candidatoRepository.findAll();
	}

	@Override
	public Candidato findById(String Id) {
		return candidatoRepository.findById(Id).get();
	}

	@Override
	public Candidato save(Candidato candidato) {
		return candidatoRepository.save(candidato);
	}

	@Override
	public void deleteById(String Id) {
		candidatoRepository.deleteById(Id);
	}

}
