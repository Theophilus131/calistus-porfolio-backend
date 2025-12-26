package com.calistus.caliblog.service;


import com.calistus.caliblog.data.model.Post;
import com.calistus.caliblog.dto.request.CreatePostRequest;
import com.calistus.caliblog.dto.response.PostResponse;
import com.calistus.caliblog.exception.AccessDeniedException;
import com.calistus.caliblog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;


    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }


    public PostResponse createPost(CreatePostRequest request, String autherId){

        Post post = new Post();

        post.setTitle(request.getTitle());
        post.setSlug(generateSlug(request.getTitle()));
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setTags(request.getTags());
        post.setPublished(false);
        post.setAuthorId(autherId);
        post.setCreatedAt(Instant.now());

        return mapToResponse(postRepository.save(post));
    }

    private String generateSlug(String title){

        return title.toLowerCase().replaceAll(" ", "-");
    }

    public List<Post> getPublishedPosts(){

        return postRepository.findAllByPublishedTrue();
    }

    public Post getPostById(String id){

        return postRepository.findById(id).orElseThrow();
    }

    public void deletePost(String id){

        postRepository.deleteById(id);
    }

    public PostResponse updatePost(String id, CreatePostRequest request){



        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found for this id :: " + id));


        String currentUsername = post.getAuthorId();
        if (!post.getAuthorId().equals(currentUsername)) {
            try {
                throw new AccessDeniedException("You are not the owner of this post");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setTags(request.getTags());
        post.setPublished(false);
        post.setUpdatedAt(Instant.now());
        post.setSlug(generateSlug(request.getTitle()));

        Post updatedPost = postRepository.save(post);

        return mapToResponse(updatedPost);

    }



    private PostResponse mapToResponse(Post post) {

        PostResponse response = new PostResponse();
        response.setTitle(post.getTitle());
        response.setSlug(generateSlug(post.getTitle()));
        response.setContent(post.getContent());
        response.setCategory(post.getCategory());
        response.setTags(post.getTags());
        response.setCreatedAt(Instant.now().toString());
        return response;
    }


}
