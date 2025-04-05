package com.example.aswe.demo.controllers;

import java.util.List;
import com.example.aswe.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.aswe.demo.repositories.UserRepository;
@RestController
@RequestMapping("/users")
public class UserController {
 
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updated) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updated.getUsername());
            user.setEmail(updated.getEmail());
            user.setPassword(updated.getPassword());
            user.setRole(updated.getRole());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
    return userRepository.findById(id)
            .map(user -> {
                userRepository.delete(user);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
}
}

