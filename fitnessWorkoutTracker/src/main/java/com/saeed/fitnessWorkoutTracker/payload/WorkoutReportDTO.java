package com.saeed.fitnessWorkoutTracker.payload;

import java.time.LocalDate;

public class WorkoutReportDTO {

    private Long totalWorkouts;
    private Long completedWorkouts;
    private Double completionPercentage;
    private LocalDate date;

    // Constructor to match the query result
    public WorkoutReportDTO(Long totalWorkouts, Long completedWorkouts, Double completionPercentage, LocalDate date) {
        this.totalWorkouts = totalWorkouts;
        this.completedWorkouts = completedWorkouts;
        this.completionPercentage = completionPercentage;
        this.date = date;
    }

    // Getters and setters
    public Long getTotalWorkouts() {
        return totalWorkouts;
    }

    public void setTotalWorkouts(Long totalWorkouts) {
        this.totalWorkouts = totalWorkouts;
    }

    public Long getCompletedWorkouts() {
        return completedWorkouts;
    }

    public void setCompletedWorkouts(Long completedWorkouts) {
        this.completedWorkouts = completedWorkouts;
    }

    public Double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
