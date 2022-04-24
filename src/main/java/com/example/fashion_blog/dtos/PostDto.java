package com.example.fashion_blog.dtos;

import com.example.fashion_blog.entities.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least two characters")
    private String title;
    @NotEmpty
    @Size(min = 2, message = "Post description should have at least two characters")
    private String description;
//    @NotEmpty
//    @Size(min = 2, message = "Post description should have at least two characters")
    @NotNull
    private long likeCount;
    private String categoryName;


    @NotEmpty
    private String content;
//    private Set<CommentDto> comments;
}

