package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    @Value("${api.endpoint.url.post}")
    private String BASE_URL;

    private final RestTemplate restTemplate;
    private final PostsRepository postsRepository;


    public ResponseEntity<List<Posts>> getAllPosts(){
//        TODO : GET DATA EXTERNAL POST AND CHANGE BE POST TYPE
        ResponseEntity<Posts[]> apiResponse = restTemplate.getForEntity(BASE_URL, Posts[].class);

//        TODO : GET LIST FROM EXTERNAL DB
        List<Posts> externalPosts = List.of(apiResponse.getBody());

//        TODO : GET ALL DATA POST DB
        List<Posts> dbPosts = postsRepository.findAll();

//        TODO : Combine Result
        dbPosts.addAll(externalPosts);

//        TODO : Return the combined List
        return ResponseEntity.ok(dbPosts);
    }


    public ResponseEntity<String> getPostsById(Long id){
        return responsetMethod(restTemplate.getForEntity(BASE_URL+id, String.class),"Failed to load data");
    }

    public ResponseEntity<String> createPosts(Posts posts){
//        TODO : Mengatur header permintaan
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        TODO : membungkus data permintaan dalam HttpEntity
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts, headers);
//        TODO : RESPONSE
        postsRepository.save(posts);
        return responsetMethod(restTemplate.postForEntity(BASE_URL, requestEntity, String.class), "Failed to Create data");
    }

    private ResponseEntity<String> responsetMethod(ResponseEntity<String> restTemplate, String message){
        ResponseEntity<String> responseEntity = restTemplate;
        if (responseEntity.getStatusCode().is2xxSuccessful()){
            String responseBody = responseEntity.getBody();
            return ResponseEntity.ok(responseBody);
        }
        return ResponseEntity.status(responseEntity.getStatusCode()).body(message);
    }
}
