package com.example.fashion_blog.services;

import com.example.fashion_blog.dtos.SignupDto;
import com.example.fashion_blog.entities.User;

public interface UserService {


    User signUp(SignupDto signupDto);
    boolean usernameExists(String username);
    boolean emailExists(String email);

}
