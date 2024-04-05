package com.example.mongodb.post;

import com.example.mongodb.search.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
// @CrossOrigin(origins = "https://localhost:3000") - Used for React
public class PostController {

    private final PostRepository postRepository;
    private final SearchRepository searchRepository;


    @GetMapping("/posts")
    public List<Post> getAllPosts()
    {
        return this.postRepository.findAll();
    }

    @GetMapping("/posts/{text}")
    public List<Post> getFilteredPosts(@PathVariable String text)
    {

        return this.searchRepository.findByText(text);
    }


    @PostMapping("/post")
    public Post addPost(@RequestBody Post post)
    {
        return this.postRepository.save(post);
    }

}
