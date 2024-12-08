package com.example.labuser2.controller;

import com.example.labuser2.entity.User;
import com.example.labuser2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/posgr/profile")
public class Profile {

    @Autowired
    private UserService userService;

    private final String UPLOAD_DIR = "C:\\Users\\whyco\\IdeaProjects\\LabUser2\\src\\main\\resources\\static\\uploads\\";

    @GetMapping
    public String getProfile(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute User user,
            @RequestParam(value = "imageFile", required = false) MultipartFile profileImage,
            Principal principal,
            Model model
    ) throws IOException {
        try {
            User currentUser = userService.findByUsername(principal.getName());

            currentUser.setUsername(user.getUsername());
            currentUser.setEmail(user.getEmail());


            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
                currentUser.setPassword(encodedPassword);
            }

            if (profileImage != null && !profileImage.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
                Path imagePath = Paths.get(UPLOAD_DIR, fileName);

                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                profileImage.transferTo(imagePath.toFile());

                userService.updateUserPhoto(currentUser.getUserId(), currentUser, fileName);
            } else {
                userService.updateUserPhoto(currentUser.getUserId(), currentUser, currentUser.getProfileImage());
            }

            return "redirect:/posgr/profile";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "profile";
        }
    }
}



