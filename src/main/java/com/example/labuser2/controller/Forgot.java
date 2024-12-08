package com.example.labuser2.controller;

import com.example.labuser2.entity.User;
import com.example.labuser2.service.EmailService;
import com.example.labuser2.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/posgr")
public class Forgot {

    private final UserService userService;
    private final EmailService emailService;

    public Forgot(UserService userService,  EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/forgot")
    public String forgotPasswordPage() {
        return "forgotPassword";
    }
    @PostMapping("/forgot")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            userService.updateResetToken(token, user.getUserId());

            String resetLink = "http://localhost:8080/posgr/reset?token=" + token;
            emailService.sendPasswordResetEmail(user.getEmail(), resetLink);

            model.addAttribute("message", "A password reset link has been sent to your email.");
        } else {
            model.addAttribute("error", "No account found with that email.");
        }

        return "forgotPassword";
    }

    @GetMapping("/reset")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        Optional<User> userOptional = userService.findByResetToken(token);

        if (userOptional.isPresent()) {
            model.addAttribute("token", token);
            return "reset";
        } else {
            model.addAttribute("error", "Invalid or expired reset token.");
            return "forgotPassword";
        }
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword,
            Model model
    ) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userService.updatePassword(user.getUsername(), newPassword);

            model.addAttribute("message", "Your password has been reset successfully.");
            return "login";
        } else {
            model.addAttribute("error", "No account found with the provided email.");
            return "reset";
        }
    }
}
