package com.br.foliveira.recrutamento_interno.repository;

import org.springframework.data.repository.CrudRepository;

import com.br.foliveira.recrutamento_interno.model.User;

public interface IUserRepository extends CrudRepository<User, Long>{}
