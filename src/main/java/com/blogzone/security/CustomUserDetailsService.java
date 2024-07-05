package com.blogzone.security;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogzone.entity.User;
import com.blogzone.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Find user by username or email
    List<User> users = userRepository.findByEmailOrUsername(username, username);

    if (users.isEmpty()) {
        throw new UsernameNotFoundException("Invalid username or password.");
    } else if (users.size() > 1) {
        // Handle multiple users found (log an error, throw an exception, or choose one)
        throw new RuntimeException("Multiple users found for username: " + username);
    }

    User user = users.get(0);

    // Build UserDetails object
    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority("USER")));
}

}
