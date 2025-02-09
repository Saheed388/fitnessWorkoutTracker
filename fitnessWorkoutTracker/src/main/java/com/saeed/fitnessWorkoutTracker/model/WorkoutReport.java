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
    private Long workoutReportsId;
    private String image;
    private Long totalWorkouts;
    private Long completedWorkouts;
    private Double completionPercentage;
    private LocalDate startDate;
    private LocalDate endDate;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
}



