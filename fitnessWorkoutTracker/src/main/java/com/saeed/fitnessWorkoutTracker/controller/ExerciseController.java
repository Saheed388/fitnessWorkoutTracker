package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.config.AppConstants;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
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
    public ResponseEntity<ExerciseDTO> addExercise(@Valid @RequestBody ExerciseDTO exerciseDTO,
                                                  @PathVariable Long workoutId){
        ExerciseDTO savedExerciseDTO= exerciseService.addExercise(workoutId, exerciseDTO);
        return new ResponseEntity<>(savedExerciseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/exercises")
    public ResponseEntity<ExerciseResponse> getAllExercises(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_EXERCISES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ){
        ExerciseResponse exerciseResponse = exerciseService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(exerciseResponse,HttpStatus.OK);
    }
}

