package com.br.foliveira.recrutamento_interno.controller;

import java.util.ArrayList;
import java.util.List;

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
    
    @Autowired 
	private IJobRepository repository;

    @GetMapping("/vaga")
    ResponseEntity<List<Job>> getAllJobs(@RequestParam(required = false) String title) {
	    List<Job> jobs = new ArrayList<>();
		if(title == null){
			repository.findAll().forEach(jobs::add);
		}else{
			repository.findByTitleContaining(title).forEach(jobs::add);
		}

		return jobs.isEmpty() ? 
			new ResponseEntity<>(HttpStatus.NO_CONTENT)
			: new ResponseEntity<>(jobs, HttpStatus.OK);
	}

	@GetMapping("/vaga/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable("id") long id) {
		return repository.findById(id)
			.map(job -> new ResponseEntity<>(job, HttpStatus.OK))
			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	

    @PostMapping("/vaga")
	public ResponseEntity<Job> postJob(@RequestBody Job job) {	
		try {
			Job job_data = repository.save(new Job(job.getTitle(), job.getDescription()));
			return new ResponseEntity<>(job_data, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

    @PutMapping("/vaga/{id}")
	public ResponseEntity<Job> putJob(@PathVariable("id") long id, @RequestBody Job job) {
		return repository.findById(id)
			.map(job_updated -> {
				job_updated.setTitle(job.getTitle());
				job_updated.setDescription(job.getDescription());
				return new ResponseEntity<>(repository.save(job_updated), HttpStatus.OK);
			}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

    @DeleteMapping("/vaga/{id}")
	public ResponseEntity<HttpStatus> deleteJobById(@PathVariable("id") long id) {
	    try {
	        repository.deleteById(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	}
    
}
