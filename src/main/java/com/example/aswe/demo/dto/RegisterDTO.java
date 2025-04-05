package com.example.aswe.demo.dto;

import java.util.Objects;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterDTO {

    private Integer id;

    @NotBlank(message = "Enter your username")
    private String username;

    @NotBlank(message = "Enter your email")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Enter your password")
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Re-enter your password")
    private String cpassword;

    public RegisterDTO() {}

    public RegisterDTO(Integer id, String username, String email, String password, String cpassword) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.cpassword = cpassword;
    }

    // Getters and Setters
    public Integer getid() {
        return id;
    }
    public void setid(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }
    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public boolean isPasswordMatching() {
        return password != null && password.equals(cpassword);
    }

    public String hashPassword(BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisterDTO)) return false;
        RegisterDTO that = (RegisterDTO) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(username, that.username) &&
               Objects.equals(email, that.email) &&
               Objects.equals(password, that.password) &&
               Objects.equals(cpassword, that.cpassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, cpassword);
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
