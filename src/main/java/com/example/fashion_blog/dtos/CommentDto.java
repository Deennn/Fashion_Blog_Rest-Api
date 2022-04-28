package com.example.fashion_blog.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class CommentDto {

    private Long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    @NotEmpty(message = "Body should not be null or empty")
    private String body;
}