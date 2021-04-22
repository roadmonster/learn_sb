package com.lihao.springredditclone.controller;

import com.lihao.springredditclone.dto.PostRequest;
import com.lihao.springredditclone.dto.PostResponse;
import com.lihao.springredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(
            @RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity("Post created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubredditId(Long id){
        return status(HttpStatus.OK).body(postService.getPostBySubreddit(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUserName(String username){
        return status(HttpStatus.OK).body(postService.getPostsByUserName(username));
    }

}
