package com.example.fashion_blog.services.servicesImpl;


import com.example.fashion_blog.dtos.CommentDto;
import com.example.fashion_blog.entities.Comment;
import com.example.fashion_blog.entities.Post;
import com.example.fashion_blog.exceptions.BlogApiException;
import com.example.fashion_blog.exceptions.ResourceNotFoundException;
import com.example.fashion_blog.repositories.CommentRepository;
import com.example.fashion_blog.repositories.PostRepository;
import com.example.fashion_blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
       Comment comment = mapToEntity(commentDto);
       Post post  = postRepository.findById(postId).orElseThrow(() ->  new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        return mapToDto(commentRepository.save(comment));


    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(this::mapToDto).toList();
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment"
        , "id", commentId));

        if  (!comment.getPost().getId().equals(post.getId()))  {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
        }
      return mapToDto(comment);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment"
                , "id", commentId));

        if  (!comment.getPost().getId().equals(post.getId()))  {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to this post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(comment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment"
                , "id", commentId));

        if  (!comment.getPost().getId().equals(post.getId()))  {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to this post.");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment) {

        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
