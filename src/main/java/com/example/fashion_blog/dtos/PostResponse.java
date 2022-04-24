package com.example.fashion_blog.dtos;

import com.example.fashion_blog.entities.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;
//    private Category category;
//    private long likeCount;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
