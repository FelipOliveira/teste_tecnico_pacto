package com.br.foliveira.recrutamento_interno;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.br.foliveira.recrutamento_interno.model.User;
import com.br.foliveira.recrutamento_interno.repository.IUserRepository;

@DataJpaTest
public class UserJpaUnitTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUserRepository repository;

    @Test
    public void getUserById_returnUserDataById(){
        List<User> users = Stream.of(
            new User("name 1","email1@email.com", null),
            new User("name 2","email2@email.com", null),
            new User("name 3","email3@email.com", null)
        ).collect(Collectors.toList());

        users.forEach(entityManager::persist);
        User userData = repository.findById(users.get(1).getId()).get();
        assertThat(userData).isEqualTo(users.get(1));
    }

    @Test
    public void postUser_returnsSavedUserData(){
        User user = new User("name1", "email@test.com", null);

        assertThat(user).hasFieldOrPropertyWithValue("name", "name1");
        assertThat(user).hasFieldOrPropertyWithValue("email", "email@test.com");
    }

    @Test
    public void putUser_returnsUpdatedUserData(){
        List<User> users = Stream.of(
            new User("name 1","email1@email.com", null),
            new User("name 2","email2@email.com", null)
        ).collect(Collectors.toList());
        users.forEach(entityManager::persist);

        User updatedUser = new User("updated name", "updated@email.com", new HashSet<>());
        User userData = repository.findById(users.get(1).getId()).get();
        userData.setName(updatedUser.getName());
        userData.setEmail(updatedUser.getEmail());
        repository.save(userData);

        User checkUser = repository.findById(users.get(1).getId()).get();
        assertThat(checkUser.getId()).isEqualTo(users.get(1).getId());
        assertThat(checkUser.getName()).isEqualTo(updatedUser.getName());
        assertThat(checkUser.getEmail()).isEqualTo(updatedUser.getEmail());
    }

    @Test
    public void deleteUserById_returnsListWithTwoUsers(){
        List<User> users = Stream.of(
            new User("name 1","email1@test.com", null),
            new User("name 2","email2@test.com", null),
            new User("name 3","email3@test.com", null)
        ).collect(Collectors.toList());
        
        users.forEach(entityManager::persist);
        repository.deleteById(users.get(1).getId());

        Iterable<User> userData = repository.findAll();
        assertThat(userData).hasSize(2).contains(users.get(0), users.get(2));
    }
}
