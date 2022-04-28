package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.SignupDto;
import com.example.fashion_blog.entities.Role;
import com.example.fashion_blog.entities.User;
import com.example.fashion_blog.exceptions.ResourceNotFoundException;
import com.example.fashion_blog.repositories.RoleRepository;
import com.example.fashion_blog.repositories.UserRepository;
import com.example.fashion_blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User signUp(SignupDto signupDto) {
        User user = new User();

        user.setFullName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));


        Role role = Role.builder()
                .name("ROLE_USER")
                .build();

        user.setRoles(Collections.singleton(role));

        Optional<User> savedUser = userRepository.findByEmail(user.getEmail());

        if (savedUser.isPresent()) throw new ResourceNotFoundException("user", "userId", user.getId());
       return userRepository.save(user);
    }


    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
