package com.example.aswe.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.aswe.demo.dto.RegisterDTO;
import com.example.aswe.demo.models.User;
import com.example.aswe.demo.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Save new user to the database
    public void saveUser(RegisterDTO registerDTO) {
        // Hash the password using BCrypt
        String hashedPassword = registerDTO.hashPassword(passwordEncoder);
        
        // Create the User entity from the UserDTO
        User user = new User(null, registerDTO.getUsername(), registerDTO.getEmail(), hashedPassword, "user");
        try {
            // Save the user to the database
            userRepository.save(user);
            logger.info("Saved new user with email: {}", registerDTO.getEmail());
        } catch (Exception e) {
            logger.error("Error saving user: {}", e.getMessage(), e);
            throw new RuntimeException("User could not be saved.");
        }

        
    }

    // Validate user login (check credentials)
    public boolean getUser(String email, String password, HttpSession session) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("user", user);  // Store user in session
            logger.info("User logged in successfully: {}", email);
            return true;
        }
        logger.warn("Invalid login attempt for email: {}", email);
        return false;
    }
    
    // Check if email exists in the database
    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Get user details from the database
    public User getUserDetails(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}