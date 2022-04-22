package com.example.fashion_blog.repositories;

import com.example.fashion_blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);
}
