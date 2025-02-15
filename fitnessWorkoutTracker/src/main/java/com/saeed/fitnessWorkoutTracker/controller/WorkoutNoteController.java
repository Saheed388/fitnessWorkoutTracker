package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteResponse;
import com.saeed.fitnessWorkoutTracker.security.jwt.JwtUtils;
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
    JwtUtils jwtUtils;

    @Autowired
    WorkoutNoteService workoutNoteService;

    @PostMapping("/workouts/{workoutId}/workoutnote")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> addWorkoutNote(
            @Valid @RequestBody WorkoutNoteDTO workoutNoteDTO,
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
        // Add workout note
        WorkoutNoteDTO savedWorkoutNoteDTO = workoutNoteService.addWorkoutNote(username, workoutId, workoutNoteDTO);
        return ResponseEntity.ok(
                new ApiResponse<>("Added successfully", HttpStatus.OK.value(), savedWorkoutNoteDTO)
        );
    }



    @GetMapping("/workoutnotes")
    public ResponseEntity<ApiResponse<WorkoutNoteResponse>> getAllWorkoutNote(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKOUTNOTE_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            @RequestHeader("Authorization") String token
    ) {
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        ApiResponse<WorkoutNoteResponse> response = workoutNoteService.getAllWorkoutNote(username, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/workout/{workoutId}/workoutnotes")
    public ResponseEntity<ApiResponse<WorkoutNoteResponse>> getWorkoutNoteByWorkout(
            @PathVariable Long workoutId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKOUTNOTE_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            @RequestHeader("Authorization") String token) {

        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        WorkoutNoteResponse workoutNoteResponse = workoutNoteService.searchNoteByWorkout(username, workoutId, pageNumber, pageSize, sortBy, sortOrder);
        ApiResponse<WorkoutNoteResponse> response = new ApiResponse<>("Successfully retrieved workout note by workoutId", HttpStatus.OK.value(),workoutNoteResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/workoutnote/{workoutNoteId}")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> getWorkoutNoteById(@PathVariable Long workoutNoteId,
                                                                          @RequestHeader("Authorization") String token){
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        WorkoutNoteDTO workoutNoteDTO = workoutNoteService.getWorkoutNoteById(username, workoutNoteId);
        if (workoutNoteDTO != null)
            return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved workout note by workoutNoteId ",HttpStatus.OK.value(), workoutNoteDTO), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PutMapping("/workoutnote/{workoutNoteId}")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> updateWorkoutNote(@Valid @RequestBody WorkoutNoteDTO workoutNoteDTO,
                                                                   @PathVariable Long workoutNoteId,
                                                                         @RequestHeader("Authorization") String token) {
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        WorkoutNoteDTO updatedWorkoutNoteDTO = workoutNoteService.updateWorkoutNote(username, workoutNoteId, workoutNoteDTO);
        return new ResponseEntity<>(new ApiResponse<>("Updated Successfully",HttpStatus.OK.value(), updatedWorkoutNoteDTO), HttpStatus.OK);
    }

    @DeleteMapping("/workoutnote/{workoutNoteId}")
    public ResponseEntity<ApiResponse<WorkoutNoteDTO>> deleteWorkoutNote(@PathVariable Long workoutNoteId,
                                                                         @RequestHeader("Authorization") String token){
        if (token   == null || !token.startsWith("Bearer ")){
            throw new APIException("Invalid or missing Authorization token");
        }

        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        WorkoutNoteDTO deletedWorkoutNoteDTO = workoutNoteService.deleteWorkoutNote(username, workoutNoteId);
        return new ResponseEntity<>(new ApiResponse<>("deleted successfully",HttpStatus.OK.value(), deletedWorkoutNoteDTO), HttpStatus.OK);
    }
}
