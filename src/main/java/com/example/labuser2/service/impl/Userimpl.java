package com.example.labuser2.service.impl;

import com.example.labuser2.entity.User;
import com.example.labuser2.exception.ResourceNotFound;
import com.example.labuser2.repository.UserRepository;
import com.example.labuser2.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}

