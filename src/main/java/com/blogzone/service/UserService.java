package com.blogzone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogzone.dto.UserDto;
import com.blogzone.entity.Blog;
import com.blogzone.entity.User;
import com.blogzone.helpers.CodeGenerator;
import com.blogzone.repository.BlogRepository;
import com.blogzone.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(UserDto userDto) {
        User user = new User();
        CodeGenerator codeGenerator = new CodeGenerator();
        user.setId(codeGenerator.generateUserId());
        user.setFullName(userDto.getFullName());
        if (userDto.getUsername().contains("@")) {
            user.setUsername(userDto.getUsername());
        }
        user.setUsername("@" + userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        CodeGenerator codeGenerator = new CodeGenerator();
        user.setId(codeGenerator.generateUserId());
        if (!user.getUsername().contains("@")) {
            user.setUsername(user.getUsername());
        }
        user.setUsername("@" + user.getUsername());
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public User findByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        return users.isEmpty() ? null : users.get(0);
    }

    public List<String> findAllIds() {
        List<String> allIds = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(user -> allIds.add(user.getId()));
        return allIds;
    }

    public int totalBlogs(String username) {

        List<Blog> blogs = blogRepository.findByAuthorId(username);
        if (blogs.isEmpty()) {
            return 0;
        }
        return blogs.size();
    }

    public void addToSavedBlog(String username, String blogId) {
        Optional<Blog> blogs = blogRepository.findById(blogId);
        if (blogs.isPresent()) {
            List<User> users = userRepository.findByUsername(username);
            User user = users.getFirst();
            List<String> totalSavedBlogs = user.getSavedBlogIds();
            totalSavedBlogs.add(blogId);
            user.setSavedBlogIds(totalSavedBlogs);
            UserDto userDto = convertEntityToDto(user);
            saveUser(userDto);
        }
    }

    public int getSavedBlogIds(String username) {
        List<User> users = userRepository.findByUsername(username);
        if (users.getFirst().getSavedBlogIds() != null) {

            return users.getFirst().getSavedBlogIds().size();
        }
        return 0;
    }

    public void updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (updatedUser.getFullName() != null)
            existingUser.setFullName(updatedUser.getFullName());

        if (updatedUser.getUsername() != null)
            existingUser.setUsername(updatedUser.getUsername());

        if (updatedUser.getEmail() != null)
            existingUser.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null)
            existingUser.setPassword(updatedUser.getPassword());

        if (updatedUser.getUserAvatar() != null)
            existingUser.setUserAvatar(updatedUser.getUserAvatar());

        if (updatedUser.getUserAvatarBase64() != null)
            existingUser.setUserAvatarBase64(updatedUser.getUserAvatarBase64());

        if (updatedUser.getUserBanner() != null)
            existingUser.setUserBanner(updatedUser.getUserBanner());

        if (updatedUser.getUserBannerBase64() != null)
            existingUser.setUserBannerBase64(updatedUser.getUserBannerBase64());

        if (updatedUser.getCountry() != null)
            existingUser.setCountry(updatedUser.getCountry());

        if (updatedUser.getBio() != null)
            existingUser.setBio(updatedUser.getBio());

        if (updatedUser.getUrl() != null)
            existingUser.setUrl(updatedUser.getUrl());

        if (updatedUser.getTotalBlogIds() != null)
            existingUser.setTotalBlogIds(updatedUser.getTotalBlogIds());

        if (updatedUser.getSavedBlogIds() != null)
            existingUser.setSavedBlogIds(updatedUser.getSavedBlogIds());

        if (updatedUser.getFollowingIds() != null)
            existingUser.setFollowingIds(updatedUser.getFollowingIds());

        if (updatedUser.getFollowersIds() != null)
            existingUser.setFollowersIds(updatedUser.getFollowersIds());

        if (updatedUser.getLikedBlogsIds() != null)
            existingUser.setLikedBlogsIds(updatedUser.getLikedBlogsIds());

        if (updatedUser.getDislikedBlogIds() != null)
            existingUser.setDislikedBlogIds(updatedUser.getDislikedBlogIds());

        if (updatedUser.getLikedCommentsIds() != null)
            existingUser.setLikedCommentsIds(updatedUser.getLikedCommentsIds());

        if (updatedUser.getDislikedCommentIds() != null)
            existingUser.setDislikedCommentIds(updatedUser.getDislikedCommentIds());

        userRepository.save(existingUser);
    }

}
