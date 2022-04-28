package com.example.fashion_blog.services;

import com.example.fashion_blog.dtos.GetPostResponse;
import com.example.fashion_blog.dtos.PostDto;
import com.example.fashion_blog.dtos.PostResponse;
import com.example.fashion_blog.dtos.SearchDto;
import com.example.fashion_blog.entities.Post;
import org.springframework.http.ResponseEntity;

public interface PostService {

    ResponseEntity<PostDto> create(PostDto postDto);

    ResponseEntity<PostResponse>getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    ResponseEntity<GetPostResponse> getPostById(Long id);

    ResponseEntity<PostDto> updatePost(PostDto postDto, Long id);

    ResponseEntity<String>  deletePostById(Long id);

//    Post getPostByTitle(SearchDto searchDto);
}
