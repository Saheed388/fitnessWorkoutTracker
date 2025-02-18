package com.saeed.fitnessWorkoutTracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // Ensure constructor exists
@NoArgsConstructor  // Ensure empty constructor exists (JPA might need it)
public class ExerciseDTO {
    private Long exerciseId;
    private String exerciseName;
    private Long exercisesRepetitions;
    private Long setsExercises;
    private Boolean completed;
}
