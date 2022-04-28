package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.*;
import com.example.fashion_blog.entities.Category;
import com.example.fashion_blog.entities.Comment;
import com.example.fashion_blog.entities.Post;
import com.example.fashion_blog.exceptions.ResourceNotFoundException;
import com.example.fashion_blog.repositories.CategoryRepository;
import com.example.fashion_blog.repositories.PostRepository;
import com.example.fashion_blog.services.CategoryService;
import com.example.fashion_blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

//    @Autowired



    @Override
    public ResponseEntity<PostDto> create(PostDto postDto) {
        Category category = categoryRepository.findByName(postDto.getCategoryName());

        if (category != null) {
            Post post = mapToEntity(postDto);
//            Post post = new Post();
//            post.setContent(postDto.getContent());
            System.out.println(post);
            post.setCategory(category);
            Post newPost = postRepository.save(post);
            PostDto p = mapToDto(newPost);
            return ResponseEntity.status(HttpStatus.CREATED).body(p);
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(postDto.getCategoryName());
        Category category1 = categoryService.createCategory(categoryDto);
        category1.setName(postDto.getCategoryName());
        Post post = mapToEntity(postDto);
        post.setCategory(category1);
        Post newPost1 = postRepository.save(post);
        PostDto p = mapToDto(newPost1);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);

    }

    @Override
    public ResponseEntity<PostResponse> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDto> content  = postList.stream().map(this::mapToDto).toList();
//        List<>
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);

        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalElements(posts.getTotalElements());
        return ResponseEntity.ok(postResponse);
    }

    @Override
    public ResponseEntity<GetPostResponse> getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        GetPostResponse getPostResponse = new GetPostResponse();
        getPostResponse.setId(post.getId());
        getPostResponse.setTitle(post.getTitle());
        getPostResponse.setDescription(post.getDescription());
        getPostResponse.setContent(post.getContent());
        getPostResponse.setLikeCount(post.getLikeCount());
        getPostResponse.setCategory(post.getCategory());

//        getPostResponse.setComments(post.getComments());
        return ResponseEntity.ok(getPostResponse);
    }

    @Override
    public ResponseEntity<PostDto> updatePost(PostDto postDto, Long id) {
        Post post  = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        var r =  mapToDto(updatedPost);
        return ResponseEntity.ok(r);
    }

    @Override
    public ResponseEntity<String> deletePostById(Long id) {
        postRepository.delete(postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
        return ResponseEntity.ok("Post successfully deleted");
    }

//    @Override
//    public Post getPostByTitle(SearchDto searchDto) {
//
//        List<Post> posts = postRepository.findAll();
//        Optional<Post> post = posts.stream().filter(x -> x.getTitle().equals(searchDto.getTitle())).findFirst();
////        GetPostResponse getPostResponse = new GetPostResponse();
////        getPostResponse.setId(post.getId());
////        getPostResponse.setTitle(post.getTitle());
////        getPostResponse.setDescription(post.getDescription());
////        getPostResponse.setContent(post.getContent());
////        getPostResponse.setLikeCount(post.getLikeCount());
////        getPostResponse.setCategory(post.getCategory());
//
////        getPostResponse.setComments(post.getComments());
//        return post.orElse(null);
//    }

    private Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    private PostDto mapToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

//    private Comment mapToEntity(CommentDto commentDto) {
//        return modelMapper.map(commentDto, Comment.class);
//    }
//    private CommentDto mapToDto(Comment comment) {
//        return modelMapper.map(comment, CommentDto.class);
//    }
}
