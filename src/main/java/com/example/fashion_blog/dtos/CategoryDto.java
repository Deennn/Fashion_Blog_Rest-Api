package com.example.fashion_blog.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Getter @Setter @ToString
public class CategoryDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Category name should have at least two characters")
    private String name;
}
