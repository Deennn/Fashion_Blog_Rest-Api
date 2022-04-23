package com.example.fashion_blog.controllers;


import com.example.fashion_blog.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<String> likePost(@PathVariable Long postId) {
        return likeService.likePost(postId);
//        return ResponseEntity.ok("Post Liked");
    }
}
