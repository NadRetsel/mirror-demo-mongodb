package com.example.mongodb.search;

import com.example.mongodb.post.Post;
import java.util.List;

public interface SearchRepository {

    List<Post> findByText(String text);

}
