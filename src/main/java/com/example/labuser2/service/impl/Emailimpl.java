package com.example.labuser2.service.impl;

import com.example.labuser2.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class Emailimpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email, String name, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(email);
            helper.setSubject("Title " + name);
            helper.setText(message, true);

            mailSender.send(mimeMessage);
            System.out.println("Email successfully sent to " + email);
        } catch (MessagingException e) {
            System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendNotif(String to, String subject, String text) {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Recipient email is null or empty");
        }
        if (subject == null) {
            subject = "No Subject";
        }
        if (text == null) {
            text = "No message content";
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }


    @Override
    public void sendPasswordResetEmail(String email, String resetLink) {
        String subject = "Password Reset Request";
        String message = "<p>Hello,</p>" +
                "<p>You requested to reset your password. Click the link below to reset it:</p>" +
                "<p><a href='" + resetLink + "'>Reset Password</a></p>" +
                "<p>If you didn't request this, please ignore this email.</p>";
        sendEmail(email, "Password Reset", message);

    }

}
