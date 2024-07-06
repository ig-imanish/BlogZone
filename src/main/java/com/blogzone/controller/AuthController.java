package com.blogzone.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogzone.dto.UserDto;
import com.blogzone.entity.User;
import com.blogzone.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Principal principal) {
        
        if (principal != null) {
            System.out.println(principal.getName());
            return "redirect:/home";
        }
        return "redirect:/auth/loginPage";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        model.addAttribute("user", new UserDto()); // Initialize a new UserDto for the form
        return "auth/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        // Check if email already exists
        User existingUserByEmail = userService.findUserByEmail(userDto.getEmail());
        if (existingUserByEmail != null) {
            result.rejectValue("email", null, "There is already an account registered with this email");
        }

        // Check if username already exists
        User existingUserByUsername = userService.findByUsername(userDto.getUsername());
        if (existingUserByUsername != null) {
            result.rejectValue("username", null, "This username is already taken");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        userService.saveUser(userDto);
        return "redirect:/auth/register?success";
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response, Principal principal) throws IOException {
        if (principal != null) {
            response.sendRedirect("/home");
            return;
        }
        response.sendRedirect("/auth/loginPage");
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }

        if (error != null) {
            System.out.println("Login failed with error: Invalid username or password");
        }

        return "auth/login";
    }
}
