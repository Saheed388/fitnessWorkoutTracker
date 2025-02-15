package com.saeed.fitnessWorkoutTracker.service;

import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteResponse;
import jakarta.validation.Valid;


public interface WorkoutNoteService {


    WorkoutNoteDTO addWorkoutNote(String username, Long workoutId, @Valid WorkoutNoteDTO workoutNoteDTO);

    ApiResponse<WorkoutNoteResponse> getAllWorkoutNote(String username, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    WorkoutNoteResponse searchNoteByWorkout(String username, Long workoutId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    WorkoutNoteDTO getWorkoutNoteById(String username, Long workoutNoteId);

    WorkoutNoteDTO updateWorkoutNote(String username, Long workoutNoteId, @Valid WorkoutNoteDTO workoutNoteDTO);

    WorkoutNoteDTO deleteWorkoutNote(String username, Long workoutNoteId);

}


