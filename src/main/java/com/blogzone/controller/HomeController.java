package com.blogzone.controller;

import java.security.Principal;
import java.util.ArrayList;
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
    public String getBlogs(Model model, @RequestParam(defaultValue = "0", required = false) int numOfBlogs, Principal principal) {
        addUserInfoToModel(model, principal);
        List<Blog> blogs = blogService.findAllBlogs();

        if(blogs.size() <= 10){
            model.addAttribute("blogs", blogs);
            return "index";
        }
        List<Blog> blogsList = new ArrayList<>();

        int n = 10 + numOfBlogs;
        if (n > blogs.size()){
            n = blogs.size() - 1;
        }
        for(int i = 0; i < n; i++){
            blogsList.add(blogs.get(i));
        }
        model.addAttribute("numOfBlogs", n);
        model.addAttribute("blogs", blogsList);
        return "index"; 
    }

    @GetMapping("/home")
    public String getHome() {
        return "redirect:/";
    }

    @GetMapping("/setting_page")
    public String setting_page(Model model, Principal principal) {
        addUserInfoToModel(model, principal);
        return "setting_page";
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
