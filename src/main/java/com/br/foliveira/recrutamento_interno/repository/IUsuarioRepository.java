package com.br.foliveira.recrutamento_interno.repository;

import org.springframework.data.repository.CrudRepository;

import com.br.foliveira.recrutamento_interno.model.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{}
