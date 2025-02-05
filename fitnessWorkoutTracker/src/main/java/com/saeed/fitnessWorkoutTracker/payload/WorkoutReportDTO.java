package com.saeed.fitnessWorkoutTracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class WorkoutReportDTO {

    private Long workoutReportsId;

    private Long totalWorkouts;
    private Long completedWorkouts;
    private Double completionPercentage;
    private LocalDate startDate;
    private LocalDate endDate;

}
