package com.example.mongodb.search;

import com.example.mongodb.post.Post;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class SearchRepositoryImplementation implements SearchRepository {

    private final MongoClient mongoClient;
    private final MongoConverter mongoConverter;

    @Override
    public List<Post> findByText(String text)
    {
        final List<Post> posts = new ArrayList<>();

        MongoDatabase database = this.mongoClient.getDatabase("joblisting");
        MongoCollection<Document> collection = database.getCollection("JobPost");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$search",
                        new Document("text",
                                new Document("query", "java")
                                        .append("path", Arrays.asList("techs", "desc", "profile")))),
                new Document("$sort",
                        new Document("exp", -1L)),
                new Document("$limit", 5L)));

        result.forEach(document -> posts.add(mongoConverter.read(Post.class, document)));

        return posts;
    }
}
