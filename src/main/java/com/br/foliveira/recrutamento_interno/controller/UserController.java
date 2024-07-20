package com.br.foliveira.recrutamento_interno.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.br.foliveira.recrutamento_interno.model.User;
import com.br.foliveira.recrutamento_interno.repository.IUserRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {
    
    @Autowired
    IUserRepository repository;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        Optional<User> user_data = repository.findById(id);
        if (user_data.isPresent()) {
	        return new ResponseEntity<>(user_data.get(), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
    }
    
    @PostMapping("/usuario")
	public ResponseEntity<User> postUsuario(@RequestBody User user) {
	    try {
	    	User user_data = repository
	            .save(
                    new User(user.getName(), user.getEmail())
                );
	        return new ResponseEntity<>(user_data, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    }
	}

    @PutMapping("/usuario/{id}")
	public ResponseEntity<User> putUsuario(@PathVariable("id") long id, @RequestBody User user) {
	    Optional<User> user_data = repository.findById(id);

	    if (user_data.isPresent()) {
	    	User _user = user_data.get();
	    	_user.setName(user.getName());
	    	_user.setEmail(user.getEmail());
	        return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

    @DeleteMapping("/usuario/{id}")
	public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") long id) {
	    try {
	        repository.deleteById(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	}
}
