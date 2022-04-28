package com.example.fashion_blog.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Getter @Setter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Category name should have at least two characters")
    private String name;
}
