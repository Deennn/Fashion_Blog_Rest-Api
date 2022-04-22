package com.example.fashion_blog.services;

import com.example.fashion_blog.dtos.CategoryDto;
import com.example.fashion_blog.entities.Category;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
}
