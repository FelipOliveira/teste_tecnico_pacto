package com.br.foliveira.recrutamento_interno.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.br.foliveira.recrutamento_interno.model.Usuario;
import com.br.foliveira.recrutamento_interno.repository.IUsuarioRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class UsuarioController {
    
    @Autowired
    IUsuarioRepository repository;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable("id") long id) {
        Optional<Usuario> dados_usuario = repository.findById(id);
        if (dados_usuario.isPresent()) {
	        return new ResponseEntity<>(dados_usuario.get(), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
    }
    
    @PostMapping("/usuario")
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {
	    try {
	    	Usuario dados_usuario = repository
	            .save(
                    new Usuario(usuario.getNome(), usuario.getEmail())
                );
	        return new ResponseEntity<>(dados_usuario, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	    }
	}

    @PutMapping("/usuario/{id}")
	public ResponseEntity<Usuario> putUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
	    Optional<Usuario> usuario_data = repository.findById(id);

	    if (usuario_data.isPresent()) {
	    	Usuario _usuario = usuario_data.get();
	    	_usuario.setNome(usuario.getNome());
	    	_usuario.setEmail(usuario.getEmail());
	        return new ResponseEntity<>(repository.save(_usuario), HttpStatus.OK);
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
