package com.example.aswe.demo.repositories;

import com.example.aswe.demo.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    List<Meeting> findByTitleContaining(String keyword);
    List<Meeting> findByDate(LocalDate date);
    List<Meeting> findByDateAndTime(LocalDate date, LocalTime time);
    List<Meeting> findByDateAfter(LocalDate date);
    List<Meeting> findByTitle(String title);

}
