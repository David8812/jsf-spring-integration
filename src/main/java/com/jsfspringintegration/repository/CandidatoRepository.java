package com.jsfspringintegration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jsfspringintegration.model.Candidato;

@Repository
public interface CandidatoRepository extends MongoRepository<Candidato, String> {
}
