package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.SignupDto;
import com.example.fashion_blog.entities.Post;
import com.example.fashion_blog.entities.Role;
import com.example.fashion_blog.entities.User;
import com.example.fashion_blog.repositories.LikeRepository;
import com.example.fashion_blog.repositories.PostRepository;
import com.example.fashion_blog.repositories.UserRepository;
import com.example.fashion_blog.services.LikeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    @Test
    void likePostTest() {
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
        var principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        given(userRepository.findByEmail(principal.getUsername())).willReturn(Optional.of(userToSave));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        ResponseEntity<String> responseEntity = likeService.likePost(1L);

        Assertions.assertThat(responseEntity).isNotNull();


    }
}