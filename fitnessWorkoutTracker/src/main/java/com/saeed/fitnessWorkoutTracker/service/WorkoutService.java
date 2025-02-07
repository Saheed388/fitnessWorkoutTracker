package com.saeed.fitnessWorkoutTracker.service;

import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutResponse;
import jakarta.validation.Valid;

public interface WorkoutService {
    WorkoutResponse getAllWorkouts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    WorkoutDTO createWorkout(@Valid WorkoutDTO workoutDTO);
}
