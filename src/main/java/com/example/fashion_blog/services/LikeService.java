package com.example.fashion_blog.services;

import org.springframework.http.ResponseEntity;

public interface LikeService {

    ResponseEntity<String> likePost(long postId);
}
