package com.example.fashion_blog.dtos;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDto {
        private String name;
        private String username;
        private String email;
        private String password;
}
