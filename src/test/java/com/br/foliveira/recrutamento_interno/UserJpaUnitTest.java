package com.br.foliveira.recrutamento_interno;

import static org.assertj.core.api.Assertions.assertThat;

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
            new User("name 1","email1@email.com"),
            new User("name 2","email2@email.com"),
            new User("name 3","email3@email.com")
        ).collect(Collectors.toList());

        users.forEach(entityManager::persist);
        User user_data = repository.findById(users.get(1).getId()).get();
        assertThat(user_data).isEqualTo(users.get(1));
    }

    @Test
    public void postUser_returnsSavedUserData(){
        User user = new User("name1", "email@test.com");

        assertThat(user).hasFieldOrPropertyWithValue("name", "name1");
        assertThat(user).hasFieldOrPropertyWithValue("email", "email@test.com");
    }

    @Test
    public void putUser_returnsUpdatedUserData(){
        List<User> users = Stream.of(
            new User("name 1","email1@email.com"),
            new User("name 2","email2@email.com")
        ).collect(Collectors.toList());
        users.forEach(entityManager::persist);

        User updated_user = new User("updated name", "updated@email.com");
        User user_data = repository.findById(users.get(1).getId()).get();
        user_data.setName(updated_user.getName());
        user_data.setEmail(updated_user.getEmail());
        repository.save(user_data);

        User check_user = repository.findById(users.get(1).getId()).get();
        assertThat(check_user.getId()).isEqualTo(users.get(1).getId());
        assertThat(check_user.getName()).isEqualTo(updated_user.getName());
        assertThat(check_user.getEmail()).isEqualTo(updated_user.getEmail());
    }

    @Test
    public void deleteUserById_returnsListWithTwoUsers(){
        List<User> users = Stream.of(
            new User("name 1","email1@test.com"),
            new User("name 2","email2@test.com"),
            new User("name 3","email3@test.com")
        ).collect(Collectors.toList());
        
        users.forEach(entityManager::persist);
        repository.deleteById(users.get(1).getId());

        Iterable<User> user_data = repository.findAll();
        assertThat(user_data).hasSize(2).contains(users.get(0), users.get(2));
    }
}
