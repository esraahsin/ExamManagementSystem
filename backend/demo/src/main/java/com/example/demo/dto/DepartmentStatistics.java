package com.example.demo.dto;

// Statistics DTO
public class DepartmentStatistics {
    private Long totalExams;
    private Long pendingValidations;
    private Long upcomingExams;

    // Getters and Setters
    public Long getTotalExams() { return totalExams; }
    public void setTotalExams(Long totalExams) { this.totalExams = totalExams; }
    
    public Long getPendingValidations() { return pendingValidations; }
    public void setPendingValidations(Long pendingValidations) { this.pendingValidations = pendingValidations; }
    
    public Long getUpcomingExams() { return upcomingExams; }
    public void setUpcomingExams(Long upcomingExams) { this.upcomingExams = upcomingExams; }
}