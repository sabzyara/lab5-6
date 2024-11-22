package com.example.labuser2.service;

import com.example.labuser2.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User findByInfo(String username, String password);

    User getUserById(Long user_id);

    List<User> getAllUsers();

    User updateUser(Long user_id , User updatedUser);

    boolean deleteUser(Long user_id);

    User findByUsername(String username);
}
