package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.dtos.GetPostResponse;
import com.example.fashion_blog.dtos.PostDto;
import com.example.fashion_blog.dtos.PostResponse;
import com.example.fashion_blog.entities.Category;
import com.example.fashion_blog.entities.Post;
import com.example.fashion_blog.repositories.CategoryRepository;
import com.example.fashion_blog.repositories.PostRepository;
import com.example.fashion_blog.services.CategoryService;
import com.example.fashion_blog.services.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private  CategoryService categoryService;
    @Mock
    private ModelMapper modelMapper;


    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void givenPostObject_whenSaveObject_thenReturnPostObject(){
        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("ps3")
                .content("something")
                .description("nice")
                .categoryName(category.getName())
                .likeCount(1)
                .build();
        System.out.println(postDto);

        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .category(category)
                .likeCount(postDto.getLikeCount())
//                .comments("kk")
                .build();
//        Post post = modelMapper.map(postDto, Post.class);

        given(categoryRepository.findByName(any())).willReturn(category);
        given(modelMapper.map(any(), eq(Post.class))).willReturn(post);
        given(modelMapper.map(any(), eq(PostDto.class))).willReturn(postDto);
        given(postRepository.save(any())).willReturn(post);
        System.out.println(post);

        ResponseEntity<PostDto>  postDtoResponseEntity = postService.create(postDto);
        System.out.println(postDtoResponseEntity);
        Assertions.assertThat(postDtoResponseEntity.getBody()).isNotNull();
        assertThat(postDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(categoryRepository, times(1)).findByName(any());
        verify(postRepository, atLeastOnce()).save(any());
        verify(modelMapper, times(2)).map(any(), any());



    }

    @Test
    void givenPostObjectIsNull_whenSaveObject_thenReturnPostObject(){
        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("ps3")
                .content("something")
                .description("nice")
                .categoryName(category.getName())
                .likeCount(1)
                .build();
        System.out.println(postDto);

        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .category(category)
                .likeCount(postDto.getLikeCount())
//                .comments("kk")
                .build();

        given(categoryRepository.findByName(any())).willReturn(null);
        given(modelMapper.map(any(), eq(Post.class))).willReturn(post);
        given(categoryService.createCategory(any())).willReturn(category);
        given(modelMapper.map(any(), eq(PostDto.class))).willReturn(postDto);
        given(postRepository.save(any())).willReturn(post);
        System.out.println(post);

        ResponseEntity<PostDto>  postDtoResponseEntity = postService.create(postDto);
        System.out.println(postDtoResponseEntity);
        Assertions.assertThat(postDtoResponseEntity.getBody()).isNotNull();
        assertThat(postDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(categoryRepository, times(1)).findByName(any());
        verify(postRepository, atLeastOnce()).save(any());
        verify(modelMapper, times(2)).map(any(), any());
        verify(categoryService, atLeastOnce()).createCategory(any());




    }

    @Test
    void getAllPost() {
        List<Post> posts = List.of(new Post());
        Page<Post> pageOfPost = new PageImpl<>(posts);
        String sortDir = "asc";
        String sortBy = "title";
        int pageNo = 0;
        int pageSize = 10;
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);


        when(postRepository.findAll(pageable)).thenReturn(pageOfPost);
        ResponseEntity<PostResponse> postResponseResponseEntity  = postService.getAllPost(pageNo, pageSize, sortBy, sortDir);

        Assertions.assertThat(postResponseResponseEntity).isNotNull();

        Assertions.assertThat(postResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(postResponseResponseEntity.getBody().getPageNo()).isEqualTo(0);

//        given(postRepository.findAll()).willReturn()
    }

    @Test
    void getPostById() {
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

        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));

        ResponseEntity<GetPostResponse> getPostResponse = postService.getPostById(post.getId());
        Assertions.assertThat(getPostResponse).isNotNull();
    }

    @Test
    void getPostByIdWhenIdIsNull() {
        Post post =  new Post();

        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
//
        ResponseEntity<GetPostResponse> getPostResponse = postService.getPostById(post.getId());
        Assertions.assertThat(getPostResponse).isNotNull();
    }


    @Test
    void updatePost() {
        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("kkk")
                .content("kkkk")
                .description("kkk")
                .build();


        Post post = Post.builder()
                .id(1L)
                .title("vdfksd")
                .description("fskdhfehs")
                .content("fhksd,fsjd")
                .category(category)
                .likeCount(1)
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(modelMapper.map(any(), eq(PostDto.class))).thenReturn(postDto);
        ResponseEntity<PostDto>  postDtoResponseEntity = postService.updatePost(postDto, 1L);
        System.out.println(postDtoResponseEntity);
        Assertions.assertThat(postDtoResponseEntity.getBody().getTitle()).isEqualTo("kkk");
        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(captor.capture());
        Assertions.assertThat(captor.getValue().getTitle()).isEqualTo("kkk");
//


    }

    @Test
    void deletePostById() {

        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();



        Post post = Post.builder()
                .id(1L)
                .title("games")
                .description("dfsdkjf")
                .content("nfjfkew")
                .category(category)
                .likeCount(1)
//                .comments("kk")
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        ResponseEntity<String> responseEntity = postService.deletePostById( 1L);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Post successfully deleted");








    }
    @Test
    void getPostByTitle() {
//        Spring Security is a framework which provides various security features like: authentication, authorization to create secure Java Enterprise Applications.
    }
//    Salting is a concept that typically pertains to password hashing. Essentially, it’s a unique value that can be added to the end of the password to create a different hash value. This adds a layer of security to the hashing process, specifically against brute force attacks.
//OAuth is an open-standard authorization protocol or framework that describes how unrelated servers and services can safely allow authenticated access to their assets without actually sharing the initial, related, single logon credential. In authentication parlance, this is known as secure, third-party, user-agent, delegated authorization.
}