package com.saeed.fitnessWorkoutTracker.service;

import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutResponse;
import jakarta.validation.Valid;

public interface WorkoutService {
//    WorkoutResponse getAllWorkouts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

//    WorkoutDTO createWorkout(@Valid WorkoutDTO workoutDTO);

//    WorkoutDTO getContentById(Long workoutId);

//    WorkoutDTO deleteWorkout(Long workoutId);

    WorkoutDTO createWorkout(@Valid WorkoutDTO workoutDTO, String username);
    WorkoutDTO updateWorkout(WorkoutDTO workoutDTO, Long workoutId, String username);
}




