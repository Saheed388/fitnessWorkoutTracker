package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
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



    @PostMapping("/workouts/{workoutId}/exercise")
    public ResponseEntity<ApiResponse<ExerciseDTO>> addExercise(@Valid @RequestBody ExerciseDTO exerciseDTO,
                                                  @PathVariable Long workoutId){
        ExerciseDTO savedExerciseDTO= exerciseService.addExercise(workoutId, exerciseDTO);
        return new ResponseEntity<>(new ApiResponse<>("added successfully", savedExerciseDTO), HttpStatus.OK);
    }


    @GetMapping("/exercises")
    public ResponseEntity<ApiResponse<ExerciseResponse>> getAllExercises(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_EXERCISES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        ApiResponse<ExerciseResponse> response = exerciseService.getAllExercises(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping("/workout/{workoutId}/exercises")
    public ResponseEntity<ApiResponse<ExerciseResponse>> getExerciseByWorkout(
            @PathVariable Long workoutId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_EXERCISES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        ExerciseResponse exerciseResponse = exerciseService.searchByWorkout(workoutId, pageNumber, pageSize, sortBy, sortOrder);

        ApiResponse<ExerciseResponse> response = new ApiResponse<>("Successfully retrieved exercises by workoutId", exerciseResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/exercises/{exerciseId}")
    public ResponseEntity<ApiResponse<ExerciseDTO>> getExerciseById(@PathVariable Long exerciseId){
        ExerciseDTO exerciseDTO = exerciseService.getExerciseById(exerciseId);
        if (exerciseDTO != null)
            return new ResponseEntity<>(new ApiResponse<>("Successfully retrieved exercise by exerciseId ", exerciseDTO), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/exercises/{exerciseId}")
    public ResponseEntity<ApiResponse<ExerciseDTO>> updateExercise(@Valid @RequestBody ExerciseDTO exerciseDTO,
                                                                   @PathVariable Long exerciseId) {
        ExerciseDTO updatedExerciseDTO = exerciseService.updateExercise(exerciseId, exerciseDTO);
        return new ResponseEntity<>(new ApiResponse<>("Updated Successfully", updatedExerciseDTO), HttpStatus.OK);
    }


    @DeleteMapping("/exercises/{exerciseId}")
    public ResponseEntity<ApiResponse<ExerciseDTO>> deleteExercise(@PathVariable Long exerciseId){
        ExerciseDTO deletedExerciseDTO = exerciseService.deleteExercise(exerciseId);
        return new ResponseEntity<>(new ApiResponse<>("deleted successfully", deletedExerciseDTO), HttpStatus.OK);
    }
}

