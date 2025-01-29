package com.example.demo.exception;

// Custom Exception Classes
public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
