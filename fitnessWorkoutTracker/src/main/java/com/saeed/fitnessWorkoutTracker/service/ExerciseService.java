package com.saeed.fitnessWorkoutTracker.service;


import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import jakarta.validation.Valid;

public interface ExerciseService {
    ExerciseDTO addExercise(Long workoutId, @Valid ExerciseDTO exerciseDTO);

//    ExerciseResponse getAllExercises(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ExerciseResponse searchByWorkout(Long workoutId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


//    ExerciseDTO updateExercise(Long exerciseId, @Valid ExerciseDTO exerciseDTO);

    ExerciseDTO deleteExercise(Long exerciseId);

    ExerciseDTO updateExercise(Long exerciseId, @Valid ExerciseDTO exerciseDTO);

    ApiResponse<ExerciseResponse> getAllExercises(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
