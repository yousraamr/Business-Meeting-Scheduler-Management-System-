package com.example.aswe.demo.controllers;
import com.example.aswe.demo.models.Meeting;
import com.example.aswe.demo.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingRepository meetingRepository;

    @GetMapping
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meeting> getMeetingById(@PathVariable Integer id) {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        return meeting.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Meeting createMeeting(@RequestBody Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable Integer id, @RequestBody Meeting updatedMeeting) {
        return meetingRepository.findById(id).map(meeting -> {
            meeting.setTitle(updatedMeeting.getTitle());
            meeting.setDescription(updatedMeeting.getDescription());
            meeting.setDate(updatedMeeting.getDate());
            meeting.setTime(updatedMeeting.getTime());
            return ResponseEntity.ok(meetingRepository.save(meeting));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteMeeting(@PathVariable Integer id) {
    Optional<Meeting> meetingOptional = meetingRepository.findById(id);
    if (meetingOptional.isPresent()) {
        meetingRepository.delete(meetingOptional.get());
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}
}
