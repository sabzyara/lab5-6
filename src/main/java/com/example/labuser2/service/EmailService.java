package com.example.labuser2.service;



public interface EmailService {
    void sendEmail(String email, String name, String message);
    void sendNotif(String to, String subject, String text);
    void sendPasswordResetEmail(String email, String resetLink);
}
