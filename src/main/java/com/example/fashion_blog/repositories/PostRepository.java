package com.example.fashion_blog.repositories;

import com.example.fashion_blog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
