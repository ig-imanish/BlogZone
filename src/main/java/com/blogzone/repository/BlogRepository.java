package com.blogzone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blogzone.entity.Blog;

public interface BlogRepository extends MongoRepository<Blog, String>{

    List<Blog> findByAuthorId(String authorId);
    List<Blog> findByCategories(List<String> categories);
    @SuppressWarnings("null")
    Optional<Blog> findById(String id);
}
