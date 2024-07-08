package com.blogzone.controller;

import java.security.Principal;
import java.util.ArrayList;
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

    @GetMapping("/follow")
    public String followUser(@RequestParam String username, Principal principal, Model model) {
        if (principal == null) {
            return "auth/login-error";
        }

        User recFollowUser = userService.findByUsername(username);
        if (recFollowUser == null) {
            model.addAttribute("error", "User not found");
            return "profile";
        }

        String sentFollowUsername = principal.getName();
        User sentFollowUser = userService.findByUsername(sentFollowUsername);

        if (sentFollowUser.getFollowingIds() == null) {
            sentFollowUser.setFollowingIds(new ArrayList<>());
        }
        if (recFollowUser.getFollowersIds() == null) {
            recFollowUser.setFollowersIds(new ArrayList<>());
        }

        sentFollowUser.getFollowingIds().add(recFollowUser.getUsername());
        recFollowUser.getFollowersIds().add(sentFollowUser.getUsername());

        userService.updateUser(sentFollowUser);
        userService.updateUser(recFollowUser);

        model.addAttribute("followed", true);
        return "redirect:/profile/" + username;
    }

}
