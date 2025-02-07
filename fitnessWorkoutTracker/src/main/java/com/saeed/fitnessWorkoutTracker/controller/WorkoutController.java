package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
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
public class WorkoutController {

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

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDTO> getWorkoutById(@PathVariable Long workoutId){
        WorkoutDTO workoutDTO = workoutService.getContentById(workoutId);
        if (workoutDTO != null)
            return new ResponseEntity<>(workoutDTO, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDTO> deleteWorkout(@PathVariable Long workoutId){

        WorkoutDTO deleteWorkout = workoutService.deleteWorkout(workoutId);
        return new ResponseEntity<>(deleteWorkout, HttpStatus.OK);
    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDTO> updateCategory(@Valid @RequestBody WorkoutDTO workoutDTO,
                                                      @PathVariable Long workoutId){
        WorkoutDTO savedWorkoutDTO = workoutService.updateCategory(workoutDTO, workoutId);
        return new ResponseEntity<>(savedWorkoutDTO, HttpStatus.OK);
    }



}

