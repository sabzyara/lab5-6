package com.example.labuser2.service;

import com.example.labuser2.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    User findByInfo(String username, String password);
    User getUserById(Long user_id);
    List<User> getAllUsers();
    User updateUser(Long user_id , User updatedUser);
    boolean deleteUser(Long user_id);
    User findByUsername(String username);
    User findByRole(String role);
    User getAuthenticatedUser();
    User updateUserPhoto(Long user_id, User updatedUser, String newProfileImage);
    void updatePassword(String username, String newPassword);
    void updateResetToken(String token, Long userId);
    Optional<User> findByResetToken(String token);
    Optional<User> findByEmail(String email);
}
