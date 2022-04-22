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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

//    @Autowired



    @Override
    public PostDto create(PostDto postDto) {
        Category category = categoryRepository.findByName(postDto.getCategoryName());

        if (category != null) {
            Post post = mapToEntity(postDto);
            post.setCategory(category);
            Post newPost = postRepository.save(post);
            return mapToDto(newPost);
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(postDto.getCategoryName());
        Category category1 = categoryService.createCategory(categoryDto);
        category1.setName(postDto.getCategoryName());
        Post post = mapToEntity(postDto);
        post.setCategory(category1);
        Post newPost1 = postRepository.save(post);
        return mapToDto(newPost1);

    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDto> content  = postList.stream().map(this::mapToDto).toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setPageSize(posts.getSize());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalElements(posts.getTotalElements());
        return postResponse;
    }

    @Override
    public GetPostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        GetPostResponse getPostResponse = new GetPostResponse();
        getPostResponse.setId(post.getId());
        getPostResponse.setTitle(post.getTitle());
        getPostResponse.setDescription(post.getDescription());
        getPostResponse.setContent(post.getContent());
        getPostResponse.setCategory(post.getCategory());

//        getPostResponse.setComments(post.getComments());
        return getPostResponse;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post  = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.delete(postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
    }

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
