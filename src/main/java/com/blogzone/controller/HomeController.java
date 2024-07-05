package com.blogzone.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.blogzone.entity.Blog;
import com.blogzone.entity.User;
import com.blogzone.service.BlogService;
import com.blogzone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getIndex(Model model, Principal principal) {
        addUserInfoToModel(model, principal);
        List<Blog> blogs = blogService.findAllBlogs();
        model.addAttribute("blogs", blogs);
        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model, Principal principal) {
        addUserInfoToModel(model, principal);
        List<Blog> blogs = blogService.findAllBlogs();
        model.addAttribute("blogs", blogs);
        return "index";
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

    // @GetMapping("/profile")
    // public String profile(HttpServletRequest request, Model model, Principal
    // principal) {
    // Cookie[] cookies = request.getCookies();
    // System.out.println(principal.getName());
    // if (cookies != null) {
    // for (Cookie cookie : cookies) {
    // if ("username".equals(cookie.getName())) {
    // String username = cookie.getValue();
    // System.out.println("username " + username);
    // if (username != null && !username.isEmpty()) {
    // User user = userService.findUserByEmail(username);

    // model.addAttribute("user", user);
    // return "profile";
    // }
    // }
    // }

    // }
    // model.addAttribute("error", "user havent logined yet");
    // return "profile";
    // }
}
