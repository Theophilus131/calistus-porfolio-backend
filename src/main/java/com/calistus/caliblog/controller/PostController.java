package com.calistus.caliblog.controller;

import com.calistus.caliblog.data.model.Post;
import com.calistus.caliblog.dto.request.CreatePostRequest;
import com.calistus.caliblog.dto.response.PostResponse;
import com.calistus.caliblog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }



    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request,
                                                   Authentication authentication){
        return ResponseEntity.ok(
                postService.createPost(
                        request, authentication.getName()
                )
        );
    }

    @GetMapping
    public List<Post> getPublishedPosts(){
        return postService.getPublishedPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id){
        return postService.getPostById(id);
    }


    // ADMIN & AUTHOR ONLY
    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable String id,
            @Valid @RequestBody CreatePostRequest request) {

        return ResponseEntity.ok(
                postService.updatePost(id,request)
        );
    }

    // ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


}
