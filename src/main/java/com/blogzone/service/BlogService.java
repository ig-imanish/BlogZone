package com.blogzone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.blogzone.entity.Blog;
import com.blogzone.helpers.MarkdownFileWriter;
import com.blogzone.repository.BlogRepository;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public void saveBlog(Blog blog) {
        MarkdownFileWriter.saveMarkdownFile(blog.getAuthorId() ,blog.getId() + ".md", blog.getContent());
        blogRepository.save(blog);
    }

    public Blog findBlogById(String id) {
        Optional<Blog> blogs = blogRepository.findById(id);
        if (blogs.isEmpty())
            return null;
        return blogs.get();
    }

    public void deleteBlogById(String id) {
        MarkdownFileWriter.deleteMarkdownFile(blogRepository.findById(id).get().getAuthorId(), id);
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

    public List<Blog> findByUsername(String name) {
        return blogRepository.findByAuthorId(name);
    }

    public String currentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return currentUsername;
    }

}
