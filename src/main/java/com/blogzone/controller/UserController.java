package com.blogzone.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blogzone.entity.User;
import com.blogzone.service.BlogService;
import com.blogzone.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class UserController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public String profileById(@PathVariable String username, HttpServletRequest request, Model model,
            Principal principal) {

        if (!username.contains("@")) {
            return "redirect:/home";
        }
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "user not found");
            return "profile";
        }
        if (principal != null) {
            String currentUsername = principal.getName();
            if (currentUsername.equals(username)) {
                model.addAttribute("current", true);
                return "profile";
            }
            model.addAttribute("user", user);
        }
        return "profile";
    }
}
