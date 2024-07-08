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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blogzone.entity.Blog;
import com.blogzone.helpers.CodeGenerator;
import com.blogzone.service.BlogService;
import com.blogzone.service.UserService;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String blogs() {
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String getBlog(@PathVariable String id, Model model) {
        Blog blog = blogService.findBlogById(id);
        if(blog == null) {
            model.addAttribute("error", "Blog not found");
            return "blogs/show";
        }
        String avatar = userService.findByUsername(blog.getAuthorId()).getUserAvatarBase64();
        System.out.println(blog);
        model.addAttribute("blog", blog);
        model.addAttribute("avatar", avatar);
        return "blogs/show";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlogById(@PathVariable String id, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        String username = principal.getName();
        Blog deletedBlog = blogService.findBlogById(id);

        if (deletedBlog != null && deletedBlog.getAuthorId().equals(username)) {
            blogService.deleteBlogById(id);
            redirectAttributes.addFlashAttribute("message", "Blog with ID " + id + " deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Unable to delete blog with ID " + id);
        }
        return "redirect:/profile/" + username;
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

                    if(principal == null) {
                        return "auth/login-error";
                    }
        if (file != null && !file.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                content = reader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
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
        CodeGenerator codeGenerator = new CodeGenerator();
        blog.setId(codeGenerator.generateBlogId());
        System.out.println("saving " + blog);
        blogService.saveBlog(blog);
        model.addAttribute("blogId", blog.getId());
        model.addAttribute("message", "successfully created blog ");
        return "blogs/create_blog";
    }
}
