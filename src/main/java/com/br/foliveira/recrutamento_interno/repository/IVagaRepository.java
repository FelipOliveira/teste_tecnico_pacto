package com.br.foliveira.recrutamento_interno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.foliveira.recrutamento_interno.model.Vaga;

public interface IVagaRepository extends JpaRepository<Vaga, Long>{
	//List<Vaga> findByNameContaining(String name);
}
