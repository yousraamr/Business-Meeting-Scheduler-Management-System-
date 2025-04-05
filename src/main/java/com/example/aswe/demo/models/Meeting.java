package com.example.aswe.demo.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private LocalDate date;
    private LocalTime time;
    private String description;
    

    public Meeting() {
    }

    public Meeting(Integer id, String title, String description, LocalDate date, LocalTime time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Meeting id(Integer id) {
        setId(id);
        return this;
    }

    public Meeting title(String title) {
        setTitle(title);
        return this;
    }

    public Meeting description(String description) {
        setDescription(description);
        return this;
    }

    public Meeting date(LocalDate date) {
        setDate(date);
        return this;
    }

    public Meeting time(LocalTime time) {
        setTime(time);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Meeting)) {
            return false;
        }
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id) && Objects.equals(title, meeting.title) && Objects.equals(description, meeting.description) && Objects.equals(date, meeting.date) && Objects.equals(time, meeting.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, date, time);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }

    
}
