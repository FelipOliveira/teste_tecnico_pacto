package com.br.foliveira.recrutamento_interno;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.br.foliveira.recrutamento_interno.model.Usuario;
import com.br.foliveira.recrutamento_interno.repository.IUsuarioRepository;

@SpringBootApplication
public class RecrutamentoInternoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecrutamentoInternoApplication.class, args);
	}

	@Bean
    CommandLineRunner init(IUsuarioRepository repository) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(nome -> {
				Usuario usuario = new Usuario(nome, nome.toLowerCase() + "@domain.com");
				repository.save(usuario);
            });
            repository.findAll().forEach(System.out::println);
        };
    }

}
