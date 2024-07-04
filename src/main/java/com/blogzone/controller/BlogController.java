package com.blogzone.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    

    @GetMapping("/create")
    public String createBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "blogs/create_blog";
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute Blog blog, @RequestParam("image") MultipartFile file,
            Principal principal) {
        try {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringDate = dateFormat.format(currentDate);

            blog.setDate(currentDate);
            blog.setDateString(stringDate);
            if (principal != null) {
                blog.setAuthorId(principal.getName());
            }
            blog.setId("ID-OF-BLOG");
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getSize());
            System.out.println(file.getContentType());
            
            Binary image = new Binary(file.getBytes());
            blog.setProductImage(image);
            blog.setImageBase64(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (Exception e) {
            System.out.println(e);
            return "blogs/create_blog";
        }
        System.out.println("saving " + blog);
        blogService.saveBlog(blog);
        return "redirect:/blogs";
    }
}
