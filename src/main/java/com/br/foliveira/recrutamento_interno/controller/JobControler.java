package com.br.foliveira.recrutamento_interno.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.foliveira.recrutamento_interno.model.Job;
import com.br.foliveira.recrutamento_interno.repository.IJobRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class JobControler {
    
    @Autowired IJobRepository repository;

    @GetMapping("/vaga")
    ResponseEntity<List<Job>> getVagas(@RequestParam(required = false) String titulo) {
	    try {
	        List<Job> vagas = new ArrayList<Job>();

	        if (titulo == null)
	    	    repository.findAll().forEach(vagas::add);
	        else
	    	    //repository.findByNameContaining(titulo).forEach(vagas::add);

	        if (vagas.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }

	        return new ResponseEntity<>(vagas, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

    @PostMapping("/vaga")
	public ResponseEntity<Job> postVaga(@RequestBody Job vaga) {
	    try {
	    	Job dados_vaga = repository
	            .save(
                    new Job(vaga.getTitle(), vaga.getDescription())
                );
	        return new ResponseEntity<>(dados_vaga, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    }
	}

    @PutMapping("/vaga/{id}")
	public ResponseEntity<Job> putVaga(@PathVariable("id") long id, @RequestBody Job vaga) {
	    Optional<Job> dados_vaga = repository.findById(id);

	    if (dados_vaga.isPresent()) {
	    	Job _vaga = dados_vaga.get();
	    	_vaga.setTitle(vaga.getTitle());
	    	_vaga.setDescription(vaga.getDescription());
	        return new ResponseEntity<>(repository.save(_vaga), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

    @DeleteMapping("/vaga/{id}")
	public ResponseEntity<HttpStatus> deleteVaga(@PathVariable("id") long id) {
	    try {
	        repository.deleteById(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	}
    
}
