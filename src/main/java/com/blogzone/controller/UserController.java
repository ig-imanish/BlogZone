package com.blogzone.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogzone.entity.User;
import com.blogzone.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @SuppressWarnings("null")
    @GetMapping("/follow")
    public String followUser(@RequestParam String username,Principal principal,Model model) {
        User recFollowUser = userService.findByUsername(username);
        if(principal == null){
            model.addAttribute("error", "you are trying follow without login Huh?");
       
            return "profile";
        }
        if(recFollowUser == null){
            model.addAttribute("error", "User not found");
            return "profile";
        }
        String sentFollowUsername = principal.getName();
        User sentFollowUser = userService.findByUsername(sentFollowUsername);

        sentFollowUser.setFollowingIds(List.of(recFollowUser.getUsername()));
        recFollowUser.setFollowersIds(List.of(sentFollowUser.getUsername()));

        model.addAttribute("followed", true);
        return "profile";
    }
    
}
