package com.saeed.fitnessWorkoutTracker.service;

import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutService {




    WorkoutDTO createWorkout(@Valid WorkoutDTO workoutDTO, String username);
    WorkoutDTO updateWorkout(WorkoutDTO workoutDTO, Long workoutId, String username);

    WorkoutResponse getUserWorkouts(String username, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


    WorkoutDTO getWorkoutById(Long workoutId, String username);

    WorkoutDTO deleteWorkout(Long workoutId, String username);


}




