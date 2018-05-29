package com.jsfspringintegration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jsfspringintegration.model.Colonia;

@Repository
public interface ColoniaRepository extends MongoRepository<Colonia, String> {

	public Colonia findByCodigoPostal(String codigoPostal);
}
