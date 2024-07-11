package com.blogzone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blogzone.entity.Blog;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String>{

    List<Blog> findByAuthorId(String authorId);
    List<Blog> findByCategories(List<String> categories);
    @Override
    Optional<Blog> findById(String id);
}
