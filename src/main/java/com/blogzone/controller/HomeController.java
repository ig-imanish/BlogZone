package com.blogzone.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogzone.entity.Blog;
import com.blogzone.entity.User;
import com.blogzone.service.BlogService;
import com.blogzone.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getBlogs(Model model, @RequestParam(defaultValue = "10", required = false) int numOfBlogs, Principal principal) {
        addUserInfoToModel(model, principal);
        List<Blog> blogs = blogService.findAllBlogs();
        model.addAttribute("blogs", blogs);
        return "index"; 
    }

    @GetMapping("/home")
    public String getHome() {
        return "redirect:/";
    }

    private void addUserInfoToModel(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
    }
}
