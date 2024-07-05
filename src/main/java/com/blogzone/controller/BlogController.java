package com.blogzone.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blogzone.entity.Blog;
import com.blogzone.service.BlogService;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String getMethodName() {
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String getBlog(@PathVariable String id, Model model) {
        Blog blog = blogService.findBlogById(id);
        model.addAttribute("blog", blog);
        return "blogs/show";
    }

    @GetMapping("/create")
    public String createBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "blogs/create_blog";
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute Blog blog,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("image") MultipartFile image,
            Principal principal, Model model) throws IOException {

        if (file != null && !file.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                content = reader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
        blog.setContent(content);
        try {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringDate = dateFormat.format(currentDate);
            
            blog.setDateString(stringDate);
            if (principal != null) {
                blog.setAuthorId(principal.getName());
            }

            Binary images = new Binary(image.getBytes());
            blog.setProductImage(images);
            blog.setImageBase64(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("error", "Error while uploading image");
            return "blogs/create_blog";
        }
        System.out.println("saving " + blog);
        blogService.saveBlog(blog);
        model.addAttribute("message", "successfully created blog, Blog id is : " + blog.getId());
        return "blogs/create_blog";
    }
}
