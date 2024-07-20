package com.br.foliveira.recrutamento_interno.repository;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.foliveira.recrutamento_interno.model.Job;

public interface IJobRepository extends JpaRepository<Job, Long>{
	//List<Vaga> findByNameContaining(String name);
}
