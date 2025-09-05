package com.example.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendRegistrationEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("🎉 Welcome to ShomoySheba!");
        message.setText("Hello " + name + ",\n\nThank you for registering with ShomoySheba.\nWe're excited to have you onboard.\n\nRegards,\nShomoySheba Team");

        try {
            sender.send(message);
            System.out.println("✅ Confirmation email sent successfully to " + toEmail);
        } catch (Exception e) {
            // Log instead of throwing: don't block user registration on email error
            System.err.println("⚠️ Could not send confirmation email to " + toEmail + ": " + e.getMessage());
        }
    }

    public void sendTempPasswordEmail(String toEmail, String tempPassword) {
        try {
            InternetAddress emailAddr = new InternetAddress(toEmail);
            emailAddr.validate();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset - ShomoySheba");
            message.setText("Your temporary password is: " + tempPassword);
            sender.send(message);
        } catch (MailException | MessagingException e) {
            throw new RuntimeException("Failed to send reset email.");
        }
    }

}
