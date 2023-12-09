package com.enigma.shopeymart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello") //http://localhost:8080/hello
    public String hello(){
        return "<h1>Hello World</h1>";
    }

    @GetMapping(value = "/hello/v1") //http://localhost:8080/hello/v1
    public String[] getHobbies(){
        return new String[]{"Makan", "Tidur"};
    }
    @GetMapping(value = "/search{key}") //http://localhost:8080/search?key=doni
    public String getRequestParam(@RequestParam String key){
        return key;
    }

    @GetMapping("/search/{id}") //http://localhost:8080/search/1
    public String getById(@PathVariable String id){
        return "Data "+id;
    }

    @GetMapping("/news/{id}{keyid}") //http://localhost:8080/news/1?keyid=Pemilu
    public String getById2(@PathVariable String id, @RequestParam String keyid){
        return "Data -"+id+" "+keyid;
    }

}
