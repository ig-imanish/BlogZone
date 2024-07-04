package com.blogzone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogzone.entity.Blog;
import com.blogzone.helpers.CodeGenerator;
import com.blogzone.repository.BlogRepository;

@Service
public class BlogService {
    @Autowired
     private BlogRepository  blogRepository;
    

    public void saveBlog(Blog blog) {
        CodeGenerator codeGenerator = new CodeGenerator();
        blog.setId(codeGenerator.generateBlogId());
        blogRepository.save(blog);
    }

    public Blog findBlogById(String id) {
        return blogRepository.findById(id).orElse(null);
    }
    public void deleteBlogById(String id) {
        blogRepository.deleteById(id);
    }
    public void updateBlog(Blog blog) {
        blogRepository.save(blog);
    }

    public List<String> findAllIds() {
        List<String> allIds = new ArrayList<>();
        blogRepository.findAll().iterator().forEachRemaining(blog -> allIds.add(blog.getId()));
        return allIds;
    }
    public List<Blog> findAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        blogRepository.findAll().iterator().forEachRemaining(blogs::add);
        return blogs;
    }

}
