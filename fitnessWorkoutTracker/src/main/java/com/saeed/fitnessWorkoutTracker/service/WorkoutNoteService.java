package com.saeed.fitnessWorkoutTracker.service;

import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteResponse;
import jakarta.validation.Valid;


public interface WorkoutNoteService {

    WorkoutNoteDTO addWorkoutNote(Long workoutId, @Valid WorkoutNoteDTO workoutNoteDTO);

    ApiResponse<WorkoutNoteResponse> getAllWorkoutNote(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    WorkoutNoteResponse searchNoteByWorkout(Long workoutId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    WorkoutNoteDTO getWorkoutNoteById(Long workoutNoteId);

    WorkoutNoteDTO updateWorkoutNote(Long workoutNoteId, @Valid WorkoutNoteDTO workoutNoteDTO);
}
