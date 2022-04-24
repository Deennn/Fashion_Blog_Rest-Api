package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.SignupDto;
import com.example.fashion_blog.entities.User;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private  UserRepository userRepository;
    @Mock
    private  RoleRepository roleRepository;
    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    private  UserServiceImpl userService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
//        userRepository = Mockito.mock(UserRepository.class);
//        roleRepository = Mockito.mock(RoleRepository.class);
//        passwordEncoder = Mockito.mock(PasswordEncoder.class);
//        modelMapper = Mockito.mock(ModelMapper.class);
//        userService = new UserServiceImpl(this.userRepository, this.roleRepository, this.passwordEncoder);
    }

    @Test
    void givenUserObject_whenSaveObject_thenReturnUserObject() {
        User user1 = new User();
        SignupDto user = SignupDto.builder()
//                .id(1L)
                .name("Lawal Abideen")
                .username("deenn")
                .email("deenn@gmail.com")
//                .password(passwordEncoder.encode("12345"))
                .password("12345")
//                .roles(Collections.singleton())
                .build();
        BDDMockito.given(userRepository.save(modelMapper.map(user, User.class))).willReturn(user1);

        User user3 = userService.signUp(user);

        Assertions.assertThat(user3).isNotNull();
    }

    @Test
    void usernameExists() {
    }

    @Test
    void emailExists() {
    }
}