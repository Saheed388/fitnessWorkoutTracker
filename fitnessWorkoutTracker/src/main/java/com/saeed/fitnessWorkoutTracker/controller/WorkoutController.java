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

    @GetMapping("/workouts")
    public ResponseEntity<ApiResponse<WorkoutResponse>> getUserWorkouts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKOUTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        WorkoutResponse workoutResponse = workoutService.getUserWorkouts(username, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved your workouts", workoutResponse), HttpStatus.OK);
    }


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



    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<ApiResponse<WorkoutDTO>> getWorkoutById(@PathVariable Long workoutId,
                                                                  @RequestHeader("Authorization") String token){
        if (token == null || !token.startsWith("Bearer ")) {
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7)); // Extract username from JWT
        WorkoutDTO workoutDTO = workoutService.getWorkoutById(workoutId, username);
        if (workoutDTO != null)
            return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved workout by workoutId ", workoutDTO), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<ApiResponse<WorkoutDTO>> updateWorkout(
            @Valid @RequestBody WorkoutDTO workoutDTO,
            @PathVariable Long workoutId,
            Authentication authentication) {

        String username = authentication.getName(); // Get logged-in user's username
        WorkoutDTO updatedWorkoutDTO = workoutService.updateWorkout(workoutDTO, workoutId, username);

        return new ResponseEntity<>(new ApiResponse<>("Updated successfully", updatedWorkoutDTO), HttpStatus.OK);
    }


    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<ApiResponse<WorkoutDTO>> deleteWorkout(
            @PathVariable Long workoutId,
            @RequestHeader("Authorization") String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        WorkoutDTO deletedWorkout = workoutService.deleteWorkout(workoutId, username);
        return new ResponseEntity<>(new ApiResponse<>("Deleted successfully", deletedWorkout), HttpStatus.OK);
    }



}

