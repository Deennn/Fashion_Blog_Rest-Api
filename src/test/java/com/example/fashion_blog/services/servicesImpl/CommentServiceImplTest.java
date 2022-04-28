package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.CommentDto;
import com.example.fashion_blog.entities.Category;
import com.example.fashion_blog.entities.Comment;
import com.example.fashion_blog.entities.Post;
import com.example.fashion_blog.repositories.CommentRepository;
import com.example.fashion_blog.repositories.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void createComment() {

        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();
        Post post = Post.builder()
                .id(1L)
                .title("games")
                .description("ashsdasd")
                .content("aksdjabsad")
                .category(category)
                .likeCount(1)
//                .comments("kk")
                .build();

        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .name("deenn")
                .email("deenn@gmail.com")
                .body("nice post")
                .build();

        Comment comment = new Comment();
        when(modelMapper.map(commentDto, Comment.class)).thenReturn(comment);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(modelMapper.map(comment, CommentDto.class)).thenReturn(commentDto);

        CommentDto commentDto1 = commentService.createComment(post.getId(), commentDto);

        Assertions.assertThat(commentDto1).isNotNull();



    }

    @Test
    void getCommentsByPostId() {

        List<Comment> commentList = List.of(new Comment());

        given(commentRepository.findByPostId(1L)).willReturn(commentList);
//        when(modelMapper.map(comment, CommentDto.class)).thenReturn(commentDto);
    }

    @Test
    void getCommentById() {
        Post post = new Post();
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(modelMapper.map(comment, CommentDto.class)).thenReturn(commentDto);
        CommentDto commentDto1 = commentService.getCommentById(1L, 1L);

        Assertions.assertThat(commentDto1).isNotNull();


    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }
}