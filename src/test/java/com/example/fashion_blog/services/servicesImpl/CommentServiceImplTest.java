package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.CommentDto;
import com.example.fashion_blog.entities.Category;
import com.example.fashion_blog.entities.Comment;
import com.example.fashion_blog.entities.Post;
import com.example.fashion_blog.exceptions.BlogApiException;
import com.example.fashion_blog.repositories.CommentRepository;
import com.example.fashion_blog.repositories.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

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



        Post post = Post.builder()
                .id(1L)
                .title("games")
                .description("ashsdasd")
                .content("aksdjabsad")
//
                .likeCount(1)
//
                .build();

        Post post2 = Post.builder()
                .id(3L)
                .title("games")
                .description("ashsdasd")
                .content("aksdjabsad")
//
                .likeCount(1)
//
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .name("deenn")
                .email("deenn@gmail.com")
                .post(post)
                .body("nice")
                .build();

        CommentDto commentDto = new CommentDto();

        when(postRepository.findById(post2.getId())).thenReturn(Optional.of(post2));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
//        when(modelMapper.map(comment, CommentDto.class)).thenReturn(commentDto);
//        CommentDto commentDto1 = commentService.getCommentById(post.getId(), comment.getId());

//        Assertions.assertThat(commentDto1).;
        org.junit.jupiter.api.Assertions.assertThrows( BlogApiException.class, () -> {
            commentService.getCommentById(post2.getId(), comment.getId());
        });




    }

    @Test
    void getCommentByIds() {



        Post post = Post.builder()
                .id(1L)
                .title("games")
                .description("ashsdasd")
                .content("aksdjabsad")
//
                .likeCount(1)
//
                .build();

//        Post post2 = Post.builder()
//                .id(3L)
//                .title("games")
//                .description("ashsdasd")
//                .content("aksdjabsad")
////
//                .likeCount(1)
////
//                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .name("deenn")
                .email("deenn@gmail.com")
                .post(post)
                .body("nice")
                .build();

        CommentDto commentDto = new CommentDto();

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(modelMapper.map(comment, CommentDto.class)).thenReturn(commentDto);
        CommentDto commentDto1 = commentService.getCommentById(post.getId(), comment.getId());

        Assertions.assertThat(commentDto1).isNotNull();
//        org.junit.jupiter.api.Assertions.assertThrows( BlogApiException.class, () -> {
//            commentService.getCommentById(post.getId(), comment.getId());
//        });




    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }
}