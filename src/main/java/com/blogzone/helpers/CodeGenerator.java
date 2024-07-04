package com.blogzone.helpers;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogzone.service.BlogService;
import com.blogzone.service.UserService;

public class CodeGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 12;

    private final UserService userService;
    private final BlogService blogService;

    @Autowired
    public CodeGenerator(UserService userService, BlogService blogService) {
        this.userService = userService;
        this.blogService = blogService;
    }

    // Default constructor for cases where constructor injection isn't used
    public CodeGenerator() {
        this.userService = null;
        this.blogService = null;
    }

    private String generateCode(String prefix) {
        Set<String> existingCodes = new HashSet<>();
        if (userService != null) {
            existingCodes.addAll(userService.findAllIds());
        }
        if (blogService != null) {
            existingCodes.addAll(blogService.findAllIds());
        }

        String code;
        do {
            code = generateRandomCode();
        } while (existingCodes.contains(prefix + "-" + code));
        existingCodes.add(prefix + "-" + code);
        return prefix + "-" + code;
    }

    private String generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH + 3);
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (i > 0 && i % 4 == 0) {
                code.append('-');
            }
            code.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return code.toString().toUpperCase();
    }

    public String generateBlogId() {
        return generateCode("BLOG");
    }

    public String generateCommentId() {
        return generateCode("CMNT");
    }

    public String generateUserId() {
        return generateCode("USER");
    }
}
