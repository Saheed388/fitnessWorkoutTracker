package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
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
}

