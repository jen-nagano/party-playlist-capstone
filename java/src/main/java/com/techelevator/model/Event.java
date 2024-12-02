package com.techelevator.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

public class Event {
    private int id;
    private String name;
    private LocalDate date;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private int creator;

    public Event(int id, String name, LocalDate date, OffsetDateTime startTime, OffsetDateTime endTime, int creator) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.creator = creator;
    }

    public Event() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}
