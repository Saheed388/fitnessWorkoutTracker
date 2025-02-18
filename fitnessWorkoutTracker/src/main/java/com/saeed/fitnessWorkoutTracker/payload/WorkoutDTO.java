package com.saeed.fitnessWorkoutTracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDTO {

    private Long workoutId;
    private String title;
    private LocalDate ScheduledDate;
    private LocalDateTime createdAt;
    private Long userId;
    private Boolean completed;

}