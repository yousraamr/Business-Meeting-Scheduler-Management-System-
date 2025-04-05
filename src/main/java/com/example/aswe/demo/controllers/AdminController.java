package com.example.aswe.demo.controllers;

import com.example.aswe.demo.models.Meeting;
import com.example.aswe.demo.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/meetings")
public class AdminController {

    @Autowired
    private MeetingRepository meetingRepository;

    // List all meetings
    @GetMapping
    public String listMeetings(Model model) {
        List<Meeting> meetings = meetingRepository.findAll();
        model.addAttribute("meetings", meetings);
        return "adminDashboard"; 
    }

    // Show form to add new meeting
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("formMode", "add");
        return "adminDashboard";
    }

    // Handle creation of a new meeting
    @PostMapping("/save")
    public String saveMeeting(@ModelAttribute("meeting") Meeting meeting,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "add");
            return "adminDashboard";
        }
        meetingRepository.save(meeting);
        return "redirect:/admin/meetings";
    }

    
    // Handle update
    @PostMapping("/update/{id}")
    public String updateMeeting(@PathVariable int id,
                                @ModelAttribute("meeting") Meeting updatedMeeting,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "edit");
            return "adminDashboard";
        }
        updatedMeeting.setId(id);
        meetingRepository.save(updatedMeeting);
        return "redirect:/admin/meetings";
    }

    // Delete meeting
    @GetMapping("/delete/{id}")
    public String deleteMeeting(@PathVariable int id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid meeting ID: " + id));
        meetingRepository.delete(meeting);
        return "redirect:/admin/meetings";
    }
}
