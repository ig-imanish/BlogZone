package com.blogzone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public class UserDto {

    private String id;
    @NotEmpty
    private String fullName;

    @NotEmpty(message = "username should not be empty")
    private String username;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;

    public UserDto() {
    }

    public UserDto(String id, @NotEmpty String fullName,
            @NotEmpty(message = "username should not be empty") String username,
            @NotEmpty(message = "Email should not be empty") @Email String email,
            @NotEmpty(message = "Password should not be empty") String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", fullName=" + fullName + ", username=" + username + ", email=" + email
                + ", password=" + password + "]";
    }

    
    
}