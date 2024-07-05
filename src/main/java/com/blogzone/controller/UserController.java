package com.blogzone.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

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

        User user = userService.findByUsername(username);
        if (user == null) {
            System.out.println("null hai user");
            model.addAttribute("error", "User not found");
            return "profile"; // Ensure profile.html handles the error attribute
        }

        // Check if the current user is viewing their own profile
        if (principal != null) {
            String currentUsername = principal.getName();
            model.addAttribute("currUsername", currentUsername);
            if (currentUsername.equals(username)) {
                model.addAttribute("current", true);
            }
        }

        List<Blog> blogs = blogService.findByUsername(username);
        int totalSavedBlogs = userService.getSavedBlogIds(username);
        int totalBlogs = blogs.size();

        model.addAttribute("totalSavedBlogs", totalSavedBlogs);
        model.addAttribute("totalBlogs", totalBlogs);
        model.addAttribute("blogs", blogs);
        model.addAttribute("current", false);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/edit")
    public String editPage(Model model,Principal principal) {

        if(principal != null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
            return "profile_edit";
        }
        model.addAttribute("error", "you havent login");
        return "profile_edit";
    }

    @PostMapping("/edit")
    public String postMethodName(@ModelAttribute User user, @RequestParam(required = false) MultipartFile banner,
            @RequestParam(required = false) MultipartFile avatar, Model model, Principal principal) throws IOException {

        if (principal == null) {
            model.addAttribute("error", "you are not owner");
            return "profile_edit";
        }
        String username = principal.getName();
        System.out.println(username);
        if (!username.equals(user.getUsername())) {
            System.out.println(user.getUsername());
            model.addAttribute("error", "you are not owner");
            return "profile_edit";
        }
            if (!avatar.isEmpty()) {
                Binary avatar1 = new Binary(avatar.getBytes());
                user.setUserAvatar(avatar1);
                user.setUserAvatarBase64(Base64.getEncoder().encodeToString(avatar.getBytes()));
            }

            if (!banner.isEmpty()) {
                Binary banner1 = new Binary(banner.getBytes());
                user.setUserBanner(banner1);
                user.setUserBannerBase64(Base64.getEncoder().encodeToString(banner.getBytes()));
            }
        userService.updateUser(user);
        System.out.println("Uploaded " + user);
        model.addAttribute("message", "successfully updated profile!");
        return "profile_edit";
    }

}
