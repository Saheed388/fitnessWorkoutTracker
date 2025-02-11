package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutResponse;
import com.saeed.fitnessWorkoutTracker.security.jwt.JwtUtils;
import com.saeed.fitnessWorkoutTracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    WorkoutService workoutService;

//    @GetMapping("/workouts")
//    public ResponseEntity<ApiResponse<WorkoutResponse>> getAllWorkouts(
//            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
//            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
//            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKOUTS_BY, required = false) String sortBy,
//            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
//        WorkoutResponse workoutResponse = workoutService.getAllWorkouts(pageNumber, pageSize, sortBy, sortOrder);
//        return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved all workouts ", workoutResponse), HttpStatus.OK);
//
//    }

    @PostMapping("/workouts")
    public ResponseEntity<ApiResponse<WorkoutDTO>> createWorkout(
            @Valid @RequestBody WorkoutDTO workoutDTO,
            @RequestHeader("Authorization") String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7)); // Extract username from JWT
        WorkoutDTO savedWorkoutDTO = workoutService.createWorkout(workoutDTO, username);
        return ResponseEntity.ok(new ApiResponse<>("Workout added successfully", savedWorkoutDTO));
    }



//    @GetMapping("/workouts/{workoutId}")
//    public ResponseEntity<ApiResponse<WorkoutDTO>> getWorkoutById(@PathVariable Long workoutId){
//        WorkoutDTO workoutDTO = workoutService.getContentById(workoutId);
//        if (workoutDTO != null)
//            return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved workout by workoutId ", workoutDTO), HttpStatus.OK);
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }

//    @DeleteMapping("/workouts/{workoutId}")
//    public ResponseEntity<ApiResponse<WorkoutDTO>> deleteWorkout(@PathVariable Long workoutId){
//
//        WorkoutDTO deleteWorkout = workoutService.deleteWorkout(workoutId);
//        return new ResponseEntity<>(new ApiResponse<>("deleted successfully", deleteWorkout), HttpStatus.OK);
//    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<ApiResponse<WorkoutDTO>> updateWorkout(
            @Valid @RequestBody WorkoutDTO workoutDTO,
            @PathVariable Long workoutId,
            Authentication authentication) {

        String username = authentication.getName(); // Get logged-in user's username
        WorkoutDTO updatedWorkoutDTO = workoutService.updateWorkout(workoutDTO, workoutId, username);

        return new ResponseEntity<>(new ApiResponse<>("Updated successfully", updatedWorkoutDTO), HttpStatus.OK);
    }




}

