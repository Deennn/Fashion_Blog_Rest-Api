package com.example.fashion_blog.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SignupDto {
        private String name;
        private String username;
        private String email;
        private String password;
}
