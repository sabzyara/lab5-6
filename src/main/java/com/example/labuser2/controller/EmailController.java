package com.example.labuser2.controller;

import com.example.labuser2.entity.User;
import com.example.labuser2.service.EmailService;
import com.example.labuser2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping("/email")
    public String showEmailForm(@RequestParam("id") Long userId, Model model) {
        User user = userService.getUserById(userId);

        if (user != null) {
            model.addAttribute("email", user.getEmail());
        } else {
            model.addAttribute("errorMessage", "User not found.");
        }

        return "email";
    }


    @PostMapping("/send")
    public String sendEmail(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("message") String message,
            Model model) {
        try {
            emailService.sendEmail(email, name, message);
            model.addAttribute("successMessage", "Email successfully sent!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error when sending an email: " + e.getMessage());
        }

        model.addAttribute("email", email);

        return "email";
    }

}



