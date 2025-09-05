package com.example.project.api;

import com.example.project.jwt.JwtUtil;
import com.example.project.entity.User;
import com.example.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserAPI {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserAPI(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    /*
        @Access to all
    */

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User rawUser = userService.getUserByEmail(user.getEmail());
        if (rawUser != null && passwordEncoder.matches(user.getPassword(), rawUser.getPassword()))
        {
            String token = new JwtUtil().generateToken(rawUser.getEmail(), rawUser.getRole());
            return ResponseEntity.ok().body("Bearer " + token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        boolean success = userService.resetPassword(email);
        if (success)
            return ResponseEntity.ok("Temporary password sent to your email.");
        else
            return ResponseEntity.status(404).body("User not found.");
    }

    /*
        @Access to all system users
    */

    @PostMapping("/profile")
    public ResponseEntity<User> getUserProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody User updatedUser, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String emailFromToken = jwtUtil.extractUsername(token);

        User actualUser = userService.getUserByEmail(emailFromToken);

        // Only allow updating name and email, not ID, password, or role
        actualUser.setName(updatedUser.getName());
        actualUser.setEmail(updatedUser.getEmail());

        userService.updateUserProfile(actualUser);
        return ResponseEntity.ok("Profile updated successfully. Log in again.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> req, HttpServletRequest request) {
        String oldPass = req.get("oldPassword");
        String newPass = req.get("newPassword");

        // Extract email from token
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractUsername(token);

        boolean changed = userService.changePassword(email, oldPass, newPass);
        if (changed)
            return ResponseEntity.ok("Password changed successfully");
        else
            return ResponseEntity.status(400).body("Old password is incorrect");
    }

    /*
        @Access to only admin
    */
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
}
