package com.saeed.fitnessWorkoutTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workout_report")
public class WorkoutReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long reportId;

    private Long totalWorkouts;
    private Long completedWorkouts;
    private Double completionPercentage;
    private LocalDate date;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    // Constructor for setting fields when creating a WorkoutReport instance
//    public WorkoutReport(Long totalWorkouts, Long completedWorkouts, Double completionPercentage, LocalDate date, User user) {
//        this.totalWorkouts = totalWorkouts;
//        this.completedWorkouts = completedWorkouts;
//        this.completionPercentage = completionPercentage;
//        this.date = date;  // No need for conversion, as date is already LocalDate
//        this.user = user;
//    }
}
