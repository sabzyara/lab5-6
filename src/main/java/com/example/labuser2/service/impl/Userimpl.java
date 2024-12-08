package com.example.labuser2.service.impl;

import com.example.labuser2.entity.User;
import com.example.labuser2.exception.ResourceNotFound;
import com.example.labuser2.repository.UserRepository;
import com.example.labuser2.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Transactional
public class Userimpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = Logger.getLogger(Userimpl.class.getName());


    @Autowired
    public Userimpl(UserRepository usersRepository) {
        this.userRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        User savedUser = userRepository.save(user);
        return savedUser;
    }


    @Override
    public User findByInfo(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }


    @Override
    public User getUserById(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFound("User is not exist :" + user_id));
        return user;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User updateUser(Long user_id, User updatedUser) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFound("User is not exist :" + user_id));


        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());

        User updatedUserO = userRepository.save(user);
        return updatedUserO;
    }
    @Override
    public User updateUserPhoto(Long user_id, User updatedUser, String newProfileImage) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFound("User is not exist :" + user_id));


        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());


        if (newProfileImage != null && !newProfileImage.isEmpty()) {
            user.setProfileImage(newProfileImage);
        }

        return userRepository.save(user);
    }



    @Override
    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userId));
        userRepository.delete(user);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
    public User findByRole(String role) {
        return userRepository.findByRole(role);
    }
    public User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return findByUsername(username);
        } else {
            throw new RuntimeException("Invalid authentication");
        }
    }


    @Override
    public void updateResetToken(String token, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userId));
        user.setResetToken(token);
        user.setTokenExpiration(Instant.now().plus(1, ChronoUnit.HOURS));
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            LOGGER.info("Updating password for user: " + username);
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setTokenExpiration(null);
            userRepository.save(user);
            LOGGER.info("Password updated successfully for user: " + username);
        } else {
            LOGGER.warning("User not found with username: " + username);
        }
    }


}





