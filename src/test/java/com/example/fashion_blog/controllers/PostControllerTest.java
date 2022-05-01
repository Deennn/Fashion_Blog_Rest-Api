//package com.example.fashion_blog.controllers;

import com.example.fashion_blog.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@ExtendWith(MockitoExtension.class)
//@WebMvcTest
//class PostControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PostService postService;
//
//    @MockBean
//    private AuthController authController;
//
//    @MockBean
//    private CategoryController categoryController;
//
//
//    @MockBean
//    private CommentController commentController;
//
//    @MockBean
//    private LikeController likeController;
//
//    @MockBean
//    private PostController postController;
//
//
////    @Test
////    void createPost() {
////        ObjectMapper objectMapper = new ObjectMapper();
////        mockMvc.perform(post("/api/posts")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString())
////                .a
////
////    }
//
//    @Test
//    void getAllPosts() {
//    }
//
//    @Test
//    void getPostById() {
//    }
//
//    @Test
//    void updatePost() {
//    }
//
//    @Test
//    void deletePost() {
//    }
//}