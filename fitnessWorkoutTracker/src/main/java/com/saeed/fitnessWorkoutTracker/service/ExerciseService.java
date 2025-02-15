package com.saeed.fitnessWorkoutTracker.service;


import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import jakarta.validation.Valid;

public interface ExerciseService {



    ExerciseDTO addExercise(String username, Long workoutId, @Valid ExerciseDTO exerciseDTO);

    ApiResponse<ExerciseResponse> getAllExercises(String username, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ExerciseResponse searchByWorkout(String username, Long workoutId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ExerciseDTO getExerciseById(String username, Long exerciseId);

    ExerciseDTO updateExercise(String username, Long exerciseId, @Valid ExerciseDTO exerciseDTO);

    ExerciseDTO deleteExercise(String username, Long exerciseId);
}
