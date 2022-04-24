package com.example.fashion_blog.services;

import com.example.fashion_blog.dtos.GetPostResponse;
import com.example.fashion_blog.dtos.PostDto;
import com.example.fashion_blog.dtos.PostResponse;
import com.example.fashion_blog.dtos.SearchDto;
import com.example.fashion_blog.entities.Post;

public interface PostService {

    PostDto create(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    GetPostResponse getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePostById(Long id);

    Post getPostByTitle(SearchDto searchDto);
}
