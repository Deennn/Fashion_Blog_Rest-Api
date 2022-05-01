package com.example.fashion_blog.services.servicesImpl;

import com.example.fashion_blog.entities.Like;

import com.example.fashion_blog.exceptions.ResourceNotFoundException;
import com.example.fashion_blog.repositories.LikeRepository;
import com.example.fashion_blog.repositories.PostRepository;
import com.example.fashion_blog.repositories.UserRepository;
import com.example.fashion_blog.services.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(UserRepository userRepository, LikeRepository likeRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public ResponseEntity<String> likePost(long postId){
        var principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(principal.getUsername());
        var byUsername = userRepository.findByEmail(principal.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("user", "USERID", 0L));
        var post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        var likedPost = isPostAlreadyLikedByUser(postId, byUsername.getId());
        if(likedPost != null){
            likeRepository.delete(likedPost);
            var likeCount = post.getLikeCount() - 1L;
            post.setLikeCount(likeCount);
            return ResponseEntity.ok("Post Unliked");
        } else {
            Like like = new Like();
            like.setPostId(postId);
            like.setUserId(byUsername.getId());

            likeRepository.save(like);

            var likeCount = post.getLikeCount() + 1L;
            post.setLikeCount(likeCount);
            return ResponseEntity.ok("Post liked");
        }
    }

    private Like isPostAlreadyLikedByUser(long postId, long userId) {
         Optional<Like>  like = likeRepository.findByUserIdAndPostId(userId, postId);
        return like.orElse(null);
    }
}
