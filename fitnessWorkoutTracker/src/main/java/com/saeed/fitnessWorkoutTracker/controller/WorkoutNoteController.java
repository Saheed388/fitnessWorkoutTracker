package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteResponse;
import com.saeed.fitnessWorkoutTracker.service.WorkoutNoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WorkoutNoteController {

    @Autowired
    WorkoutNoteService workoutNoteService;

    @PostMapping("/workouts/{workoutId}/workoutnote")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> addWorkoutNote(@Valid @RequestBody WorkoutNoteDTO workoutNoteDTO,
                                                                   @PathVariable Long workoutId){
        WorkoutNoteDTO savedWorkoutNoteDTO= workoutNoteService.addWorkoutNote(workoutId, workoutNoteDTO);
        return new ResponseEntity<>(new ApiResponse<>("added successfully", savedWorkoutNoteDTO), HttpStatus.OK);
    }

    @GetMapping("/workoutnotes")
    public ResponseEntity<ApiResponse<WorkoutNoteResponse>> getAllWorkoutNote(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKOUTNOTE_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        ApiResponse<WorkoutNoteResponse> response = workoutNoteService.getAllWorkoutNote(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/workout/{workoutId}/workoutnotes")
    public ResponseEntity<ApiResponse<WorkoutNoteResponse>> getWorkoutNoteByWorkout(
            @PathVariable Long workoutId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKOUTNOTE_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        WorkoutNoteResponse workoutNoteResponse = workoutNoteService.searchNoteByWorkout(workoutId, pageNumber, pageSize, sortBy, sortOrder);

        ApiResponse<WorkoutNoteResponse> response = new ApiResponse<>("Successfully retrieved workout note by workoutId", workoutNoteResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/workoutnote/{workoutNoteId}")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> getWorkoutNoteById(@PathVariable Long workoutNoteId){
        WorkoutNoteDTO workoutNoteDTO = workoutNoteService.getWorkoutNoteById(workoutNoteId);
        if (workoutNoteDTO != null)
            return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved workout note by workoutNoteId ", workoutNoteDTO), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PutMapping("/workoutnote/{workoutNoteId}")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> updateWorkoutNote(@Valid @RequestBody WorkoutNoteDTO workoutNoteDTO,
                                                                   @PathVariable Long workoutNoteId) {
        WorkoutNoteDTO updatedWorkoutNoteDTO = workoutNoteService.updateWorkoutNote(workoutNoteId, workoutNoteDTO);
        return new ResponseEntity<>(new ApiResponse<>("Updated Successfully", updatedWorkoutNoteDTO), HttpStatus.OK);
    }

    @DeleteMapping("/workoutnote/{workoutNoteId}")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> deleteWorkoutNote(@PathVariable Long workoutNoteId){
        WorkoutNoteDTO deletedWorkoutNoteDTO = workoutNoteService.deleteworkoutNote(workoutNoteId);
        return new ResponseEntity<>(new ApiResponse<>("deleted successfully", deletedWorkoutNoteDTO), HttpStatus.OK);
    }
}
