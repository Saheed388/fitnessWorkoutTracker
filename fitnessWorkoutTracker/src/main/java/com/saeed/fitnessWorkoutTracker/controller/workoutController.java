package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutResponse;
import com.saeed.fitnessWorkoutTracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class workoutController {

    @Autowired
    WorkoutService workoutService;

    @GetMapping("/workouts")
    public ResponseEntity<WorkoutResponse> getAllWorkouts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKOUTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        WorkoutResponse workoutResponse = workoutService.getAllWorkouts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(workoutResponse, HttpStatus.OK);
    }

    @PostMapping("/workouts")
    public ResponseEntity<WorkoutDTO> createWorkout(@Valid @RequestBody WorkoutDTO workoutDTO){
        WorkoutDTO savedWorkoutDTO = workoutService.createWorkout(workoutDTO);
        return new ResponseEntity<>(savedWorkoutDTO, HttpStatus.CREATED);
    }

}

