package com.saeed.fitnessWorkoutTracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExerciseDTO {
    private Long exercisesId;
    private String exerciseName;
    private Long setsExercises;
    private Long exercisesRepetitions;
    private Boolean completed;
    private LocalDateTime createdAt;
}
