package com.saeed.fitnessWorkoutTracker.implementation;

import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ResourceNotFoundException;
import com.saeed.fitnessWorkoutTracker.model.Exercise;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.repository.ExerciseRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutNoteRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutRepository;
import com.saeed.fitnessWorkoutTracker.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Override
    public ExerciseDTO addExercise(Long workoutId, ExerciseDTO exerciseDTO) {
            Workout workout = workoutRepository.findById(workoutId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Workout", "workoutId", workoutId));

            boolean isExerciseNotPresent = true;

            List<Exercise> exercises = workout.getExercises();
            for (Exercise value : exercises) {
                if (value.getExerciseName().equals(exerciseDTO.getExerciseName())) {
                    isExerciseNotPresent = false;
                    break;
                }
            }

            if (isExerciseNotPresent) {
                Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
                exercise.setWorkouts(workout);

                Exercise savedExercise = exerciseRepository.save(exercise);
                return modelMapper.map(savedExercise, ExerciseDTO.class);
            } else {
                throw new APIException("Product already exist!!");
            }
        }
    }

