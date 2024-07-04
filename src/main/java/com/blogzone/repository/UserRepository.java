package com.blogzone.repository;

import com.blogzone.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    List<User> findByUsername(String username);
    User findByEmailOrUsername(String email, String username);
}