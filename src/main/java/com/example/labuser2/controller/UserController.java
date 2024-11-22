package com.example.labuser2.controller;



import com.example.labuser2.entity.User;
import com.example.labuser2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;


@Controller
@RequestMapping("/posgr/user")
@AllArgsConstructor

public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login() {
            return "login";
        }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        LOGGER.info("Attempting login for username: " + username);

        User user = userService.findByUsername(username);
        if (user != null) {
            LOGGER.info("Password entered: " + password);
            LOGGER.info("Password stored (hashed): " + user.getPassword());

            if (passwordEncoder.matches(password.trim(), user.getPassword())) {
                LOGGER.info("Password matched for user: " + username);
                return "redirect:/posgr/home";
            } else {
                LOGGER.warn("Invalid password for user: " + username);
            }
        } else {
            LOGGER.warn("User not found: " + username);
        }

        model.addAttribute("loginError", "Invalid username or password!");
        return "login";
    }
    


    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user, Model model ) {
        userService.addUser(user);
        model.addAttribute("message", "User added successfully");
        return "login";
    }


    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    @PostMapping("/{id}")
    public String updateUser(@PathVariable("id") long userId, @ModelAttribute User updatedUser) {
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        User user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return "redirect:/posgr/user/users";
        } else {
            return "error";
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted");
    }
}
