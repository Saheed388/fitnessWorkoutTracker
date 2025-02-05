package com.saeed.fitnessWorkoutTracker.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class WorkoutDTO {

    private Long workoutId;
    private String title;
    private LocalDate scheduled_date;
    private LocalDate created_at;
}
