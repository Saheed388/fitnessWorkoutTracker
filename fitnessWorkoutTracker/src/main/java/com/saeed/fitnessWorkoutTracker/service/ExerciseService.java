package com.saeed.fitnessWorkoutTracker.service;


import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import jakarta.validation.Valid;

public interface ExerciseService {
    ExerciseDTO addExercise(Long workoutId, @Valid ExerciseDTO exerciseDTO);

    ExerciseResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
