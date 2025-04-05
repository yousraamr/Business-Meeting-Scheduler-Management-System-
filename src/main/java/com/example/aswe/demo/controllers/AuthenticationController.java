package com.example.aswe.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.demo.services.UserService;
import com.example.aswe.demo.dto.LoginDTO;
import com.example.aswe.demo.dto.RegisterDTO;
import com.example.aswe.demo.models.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class); 

    @Autowired
    private UserService userService;

    // Login GET
    @GetMapping("/login")
    public String login(@ModelAttribute LoginDTO loginDTO, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/"; // Redirect to home if already logged in
        }
        return "login";
    }

    // Login POST
    @PostMapping("/login")
    public String postLogin(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult bindingResult, HttpSession session) {
    if (bindingResult.hasErrors()) {
        logger.warn("Validation errors found: {}", bindingResult.getAllErrors());
        return "login";
    }

    // Log attempt to log in
    logger.info("Received login request for email: {}", loginDTO.getEmail());

    // Check if the user exists and if the password is correct
    if (!userService.getUser(loginDTO.getEmail(), loginDTO.getPassword(), session)) {
        bindingResult.addError(new FieldError("loginDTO", "password", "Incorrect email or password"));
        logger.warn("Invalid login attempt for email: {}", loginDTO.getEmail());
        return "login";
    }
    
    // Log successful login
    User user = (User) session.getAttribute("user");
    logger.info("User logged in successfully: {}", user.getEmail());

    // Redirect to the user's dashboard or a home page based on role
    if ("admin".equalsIgnoreCase(user.getRole())) {
        logger.info("Redirecting admin user to /admin/dashboard");
        return "redirect:/admin/dashboard";
    } else {
        logger.info("Redirecting regular user to home page");
        return "redirect:/";
    }
    
}


    // Register GET
    @GetMapping("/signup")
    public String signup(@ModelAttribute RegisterDTO registerDTO, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/"; // Redirect to home if already logged in
        }
        return "register";
    }

    // Register POST
    @PostMapping("/signup")
    public String postSignup(@Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result) {
        logger.info("Received signup form submission for email: {}", registerDTO.getEmail());
        
        // Validate if email already exists
        if (userService.existEmail(registerDTO.getEmail())) {
            result.addError(new FieldError("userDTO", "email", "Email address already in use"));
        }

        // Check if passwords match
        if (registerDTO.getPassword() != null && registerDTO.getCpassword() != null) {
            if (!registerDTO.getPassword().equals(registerDTO.getCpassword())) {
                result.addError(new FieldError("userDTO", "cpassword", "Passwords must match"));
            }
        }

        if (result.hasErrors()) {
            logger.info("Validation errors found: {}", result.getAllErrors());
            return "register";
        }

        // Hash the password directly in the controller
        //String hashedPassword = passwordEncoder.encode(userDTO.getUserPassword());
        //userDTO.setUserPassword(hashedPassword);

        // Save the new user
        logger.info("No validation errors, saving user to database...");
        userService.saveUser(registerDTO);

        return "redirect:/auth/login"; // Redirect to the login page after successful registration
    }


    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("Logging out user...");
        session.invalidate();
        return "redirect:/auth/login"; 
    }
}