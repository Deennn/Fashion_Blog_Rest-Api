package com.example.fashion_blog.services;

import com.example.fashion_blog.dtos.SignupDto;

public interface UserService {


    void signUp(SignupDto signupDto);
    boolean usernameExists(String username);
    boolean emailExists(String email);

}
