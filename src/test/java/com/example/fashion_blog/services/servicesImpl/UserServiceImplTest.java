package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.SignupDto;
import com.example.fashion_blog.entities.Role;
import com.example.fashion_blog.entities.User;
import com.example.fashion_blog.exceptions.ResourceNotFoundException;
import com.example.fashion_blog.repositories.RoleRepository;
import com.example.fashion_blog.repositories.UserRepository;
import com.example.fashion_blog.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;


    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {

    }

    @Test
    void givenUserObject_whenSaveObject_thenReturnUserObject() {
        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();

        System.out.println(role);

        SignupDto user = SignupDto.builder()

                .name("Lawal Abideen")
                .username("deenn")
                .email("deenn@gmail.com")

                .password("12345")

                .build();


        User userToSave = User.builder()
                .id(1L)
                .fullName(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(Collections.singleton(role))
                .build();
//
        given(userRepository.save(any())).willReturn(userToSave);
        System.out.println(userToSave);


        User savedUser = userService.signUp(user);
        System.out.println(savedUser);
        Assertions.assertThat(savedUser).isNotNull();



    }

    @Test
    void givenUserWithExistingEmail_whenSaveObject_thenReturnUserObject() {
        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();


        SignupDto user = SignupDto.builder()

                .name("Lawal Abideen")
                .username("deenn")
                .email("deenn@gmail.com")

                .password("12345")

                .build();


        User userToSave = User.builder()
                .id(1L)
                .fullName(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(Collections.singleton(role))
                .build();
        given(userRepository.findByEmail(userToSave.getEmail())).willReturn(Optional.of(userToSave));
//        given(userRepository.save(any())).willReturn(Optional.ofNullable(null));


        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            User savedUser = userService.signUp(user);
        });

        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void usernameExists() {
        String username = "deenn";
        given(userRepository.existsByUsername(username)).willReturn(true);

        boolean checker = userService.usernameExists(username);
        org.junit.jupiter.api.Assertions.assertTrue(checker);

    }

    @Test
    void emailExists() {
        String email = "deenn@gmail.com";
        given(userRepository.existsByEmail(email)).willReturn(true);

        boolean checker = userService.emailExists(email);
        org.junit.jupiter.api.Assertions.assertTrue(checker);

    }





}

