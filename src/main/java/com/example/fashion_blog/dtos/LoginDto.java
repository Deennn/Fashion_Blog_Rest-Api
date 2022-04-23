package com.example.fashion_blog.dtos;


import lombok.*;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
