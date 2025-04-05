package com.example.aswe.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import com.example.aswe.demo.models.User;

@Controller
public class HomeController {

    // Home page
     @GetMapping("/")
    public String Homepage(Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("user") != null) {
            // User is logged in, add necessary attributes to the model
            User user = (User) session.getAttribute("user");
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", "admin".equalsIgnoreCase(user.getRole()));
        } else {
            // User is not logged in
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("isAdmin", false);  // Ensure isAdmin is false for non-logged-in users
        }
        return "Landingpage"; // Return your landing page view
    }

// Admin Dashboard page (visible only to admin users)
@GetMapping("/admin/dashboard")
public String Adminpage(Model model, HttpSession session) {
    // Check if the user is logged in
    if (session.getAttribute("user") != null) {
        // Get the user from session
        User user = (User) session.getAttribute("user");
        model.addAttribute("isLoggedIn", true);
        
        // Check if the user is an admin
        if ("admin".equalsIgnoreCase(user.getRole())) {
            model.addAttribute("isAdmin", true);  // User is an admin
            return "adminDashboard";  // Show the admin dashboard page
        } else {
            // If the user is not an admin, redirect them to the home page or error page
            return "redirect:/";  // Redirect to home page
        }
    } else {
        // If the user is not logged in, redirect to login page
        model.addAttribute("isLoggedIn", false);
        model.addAttribute("isAdmin", false);
        return "redirect:/auth/login";  // Redirect to login page
    }
}


    // Profile page (only accessible when logged in)
     // Profile page (only accessible when logged in)
    @GetMapping("/profile")
    public String Profilepage(HttpSession session, Model model) {
        // Check if the user is logged in
        if (session.getAttribute("user") != null) {
            // Add attributes to model for Thymeleaf to access
            User user = (User) session.getAttribute("user");
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", "admin".equalsIgnoreCase(user.getRole()));
            model.addAttribute("user", user);
            return "profile"; // Thymeleaf profile page
        }
        // User is not logged in, redirect to login page
        return "redirect:/auth/login"; // Redirect to login page if not logged in
    }

    // Schedule page (only accessible when logged in)
    @GetMapping("/schedule")
    public String Schedulepage(HttpSession session, Model model) {
        // Check if the user is logged in
        if (session.getAttribute("user") != null) {
            // Add attributes to model for Thymeleaf to access
            User user = (User) session.getAttribute("user");
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", "admin".equalsIgnoreCase(user.getRole()));
            return "schedule"; // Thymeleaf schedule page
        }
        // User is not logged in, redirect to login page
        return "redirect:/auth/login"; // Redirect to login page if not logged in
    }
}
