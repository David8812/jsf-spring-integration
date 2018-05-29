package com.jsfspringintegration.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsfspringintegration.model.Colonia;
import com.jsfspringintegration.repository.ColoniaRepository;

@Service("coloniaService")
public class ColoniaServiceImpl implements ColoniaService {

	@Autowired
	ColoniaRepository coloniaRepository;

	@Override
	public List<Colonia> findAll() {
		return coloniaRepository.findAll();
	}

	@Override
	public Colonia findById(String Id) {
		try {
			return coloniaRepository.findById(Id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public Colonia save(Colonia colonia) {
		return coloniaRepository.save(colonia);
	}

	@Override
	public void deleteById(String Id) {
		coloniaRepository.deleteById(Id);
	}

	@Override
	public Colonia findByCodigoPostal(String codigoPostal) {
		return coloniaRepository.findByCodigoPostal(codigoPostal);
	}

}
