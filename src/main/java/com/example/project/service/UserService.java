package com.example.project.service;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService=emailService;
        this.passwordEncoder=passwordEncoder;
    }

    public int registerUser(User user) {
        user.setRole("USER");
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        emailService.sendRegistrationEmail(user.getEmail(), user.getName());
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public int updateUserProfile(User user) {
        return userRepository.updateUser(user);
    }

    public boolean changePassword(String email, String oldPassword, String newPassword) {
        User existing = userRepository.findByEmail(email.trim());

        if (existing == null) return false;

        if (passwordEncoder.matches(oldPassword, existing.getPassword())) {
            String hashedNewPassword = passwordEncoder.encode(newPassword);
            userRepository.updatePassword(email, hashedNewPassword);
            return true;
        }

        return false;
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public boolean resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;

        String tempPass = generateTempPassword();
        user.setPassword(tempPass);
        userRepository.updatePassword(user.getEmail(), tempPass);
        emailService.sendTempPasswordEmail(user.getEmail(), tempPass);
        return true;
    }





}
