package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.service.impl.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostsController {
    private final PostsService postsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Posts>> getAllPosts(){
        return postsService.getAllPosts();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPostById(@PathVariable Long id){
        return postsService.getPostsById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPosts(@RequestBody Posts posts){
        return postsService.createPosts(posts);
    }


}
