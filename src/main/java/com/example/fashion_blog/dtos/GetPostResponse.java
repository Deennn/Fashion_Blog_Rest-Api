package com.example.fashion_blog.dtos;

import com.example.fashion_blog.entities.Category;
import com.example.fashion_blog.entities.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class GetPostResponse {

    private Long id;
    private String title;
    private String description;
    private String content;
    private Category category;
//    private Set<Comment> comments;



}

