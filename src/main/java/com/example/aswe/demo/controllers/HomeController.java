package com.example.aswe.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
    @GetMapping("/")
    public ModelAndView Homepage(){
        ModelAndView mav=new ModelAndView("Landingpage");
        return mav;
    }
       @GetMapping("/login")
    public ModelAndView Loginpage(){
        ModelAndView mav=new ModelAndView("login");
        return mav;
    }
           @GetMapping("/register")
    public ModelAndView Registerpage(){
        ModelAndView mav=new ModelAndView("register");
        return mav;
    }
           @GetMapping("/admin")
    public ModelAndView Adminpage(){
        ModelAndView mav=new ModelAndView("adminDashboard");
        return mav;
    }

           @GetMapping("/profile")
    public ModelAndView Profilepage(){
        ModelAndView mav=new ModelAndView("profile");
        return mav;
    }
              @GetMapping("/schedule")
    public ModelAndView Schedulepage(){
        ModelAndView mav=new ModelAndView("schedule");
        return mav;
    }
}
