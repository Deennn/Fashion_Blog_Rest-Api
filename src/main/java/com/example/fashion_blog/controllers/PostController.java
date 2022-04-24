package com.example.fashion_blog.controllers;


import com.example.fashion_blog.dtos.GetPostResponse;
import com.example.fashion_blog.dtos.PostDto;
import com.example.fashion_blog.dtos.PostResponse;
import com.example.fashion_blog.dtos.SearchDto;
import com.example.fashion_blog.entities.Post;
import com.example.fashion_blog.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.fashion_blog.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
//       return new ResponseEntity<>(postService.create(postDto), HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(postDto));

    }

//    @PostMapping("/searchDto")
//    public ResponseEntity<Post> getPost(@RequestBody SearchDto searchDto) {
////       return new ResponseEntity<>(postService.create(postDto), HttpStatus.CREATED);
//        return ResponseEntity.ok().body(postService.getPostByTitle(searchDto));
//
//    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir ) {
        return ResponseEntity.ok(postService.getAllPost(pageNo,pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return  ResponseEntity.ok("Post deleted Successfully");
    }
}
