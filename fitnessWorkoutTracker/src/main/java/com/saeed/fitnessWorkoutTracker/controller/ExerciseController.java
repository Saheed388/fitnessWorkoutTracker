package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.security.jwt.JwtUtils;
import com.saeed.fitnessWorkoutTracker.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    JwtUtils jwtUtils;



    @PostMapping("/workouts/{workoutId}/exercise")
    public ResponseEntity<ApiResponse<ExerciseDTO>> addExercise(
            @Valid @RequestBody ExerciseDTO exerciseDTO,
            @PathVariable Long workoutId,
            @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new APIException("Invalid or missing Authorization token");
        }
        // Extract username from JWT
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7).trim());
        if (username == null || username.isEmpty()) {
            throw new APIException("Invalid JWT token. Unable to extract username.");
        }
        // Debugging log (Ensure logging is configured)
        System.out.println("Extracted Username: " + username);
        // Add exercise
        ExerciseDTO savedExerciseDTO = exerciseService.addExercise(username, workoutId, exerciseDTO);
        return ResponseEntity.ok(new ApiResponse<>("Added successfully", HttpStatus.OK.value(), savedExerciseDTO));
    }



    @GetMapping("/exercises")
    public ResponseEntity<ApiResponse<ExerciseResponse>> getAllExercises(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_EXERCISES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            @RequestHeader("Authorization") String token
    ) {
        if (token   == null || !token.startsWith("Bearer")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));

        ApiResponse<ExerciseResponse> response = exerciseService.getAllExercises(username, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/workout/{workoutId}/exercises")
    public ResponseEntity<ApiResponse<ExerciseResponse>> getExerciseByWorkout(
            @PathVariable Long workoutId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_EXERCISES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            @RequestHeader("Authorization") String token) {
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        ExerciseResponse exerciseResponse = exerciseService.searchByWorkout( username, workoutId, pageNumber, pageSize, sortBy, sortOrder);
        ApiResponse<ExerciseResponse> response = new ApiResponse<>("Successfully retrieved exercises by workoutId", HttpStatus.OK.value(),exerciseResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/exercises/{exerciseId}")
    public ResponseEntity<ApiResponse<ExerciseDTO>> getExerciseById(@PathVariable Long exerciseId,
                                                                    @RequestHeader("Authorization") String token){
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        ExerciseDTO exerciseDTO = exerciseService.getExerciseById(username, exerciseId);

        return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved exercise by exerciseId ",HttpStatus.OK.value(), exerciseDTO), HttpStatus.OK);


    }

    @PutMapping("/exercises/{exerciseId}")
    public ResponseEntity<ApiResponse<ExerciseDTO>> updateExercise(@Valid @RequestBody ExerciseDTO exerciseDTO,
                                                                   @PathVariable Long exerciseId,
                                                                   @RequestHeader("Authorization") String token){
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));

        ExerciseDTO updatedExerciseDTO = exerciseService.updateExercise(username, exerciseId, exerciseDTO);
        return new ResponseEntity<>(new ApiResponse<>("Updated Successfully", HttpStatus.OK.value(),updatedExerciseDTO), HttpStatus.OK);
    }


    @DeleteMapping("/exercises/{exerciseId}")
    public ResponseEntity<ApiResponse<ExerciseDTO>> deleteExercise(@PathVariable Long exerciseId,
                                                                   @RequestHeader("Authorization") String token){
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));

        ExerciseDTO deletedExerciseDTO = exerciseService.deleteExercise(username, exerciseId);
        return new ResponseEntity<>(new ApiResponse<>("deleted successfully",HttpStatus.OK.value(), deletedExerciseDTO), HttpStatus.OK);
    }
}

