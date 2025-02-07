package com.saeed.fitnessWorkoutTracker.service;


import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import jakarta.validation.Valid;

public interface ExerciseService {
    ExerciseDTO addExercise(Long workoutId, @Valid ExerciseDTO exerciseDTO);
}
