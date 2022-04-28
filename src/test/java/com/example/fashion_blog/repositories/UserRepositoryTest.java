package com.example.fashion_blog.repositories;

import com.example.fashion_blog.entities.Role;
import com.example.fashion_blog.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@DataJpaTest
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;



    @Test
    public void givenUserObject_whenSave_thenReturnSavedEmployee() {

//        Role role = new Role();
//        role.setName("ROLE_USER");
        User user = new User();
                user.setFullName("Lawal Abideen");
                user.setUsername("deenn");
                user.setEmail("lawal@gmail.com");
                user.setPassword("12345");

//                user.setRoles(Collections.singleton(role));

        User savedUser = userRepository.save(user);
        Assertions.assertThat(savedUser).isNotNull();
//        Assertions.assertThat(savedUser.getId())

    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByEmailOrUsername() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void existsByUsername() {
    }

    @Test
    void existsByEmail() {
    }
}