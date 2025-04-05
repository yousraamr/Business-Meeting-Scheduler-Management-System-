package com.example.aswe.demo.controllers;

import com.example.aswe.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.aswe.demo.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User updatedUser) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            updatedUser.setId(user.getId());  // Ensure the ID stays the same
            userService.updateUserProfile(updatedUser);  // Update user profile
            session.setAttribute("user", updatedUser);  // Update session user info
            return "redirect:/profile";  // Redirect to profile page
        } else {
            return "redirect:/auth/login"; // Redirect to login if not logged in
        }
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                userService.changePassword(user.getId(), currentPassword, newPassword);
                return "redirect:/profile?success=true"; // Password updated successfully
            } catch (RuntimeException e) {
                // Handle invalid password exception
                return "redirect:/profile?error=true"; // Optionally, show error on the profile page
            }
        } else {
            return "redirect:/auth/login"; // Redirect to login if not logged in
        }
    }
}
