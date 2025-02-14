package com.saeed.fitnessWorkoutTracker.implementation;

import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.exception.ResourceNotFoundException;
import com.saeed.fitnessWorkoutTracker.model.Exercise;
import com.saeed.fitnessWorkoutTracker.model.User;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.repository.ExerciseRepository;
import com.saeed.fitnessWorkoutTracker.repository.UserRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutRepository;
import com.saeed.fitnessWorkoutTracker.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
     ModelMapper modelMapper;
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;




    @Override
    public ExerciseDTO addExercise(String username, Long workoutId, ExerciseDTO exerciseDTO) {
        // Fetch user and validate
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));

        // Fetch workout and validate ownership
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout", "workoutId", workoutId));

        if (!workout.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("You are not authorized to modify this workout.");
        }
        // Check if the exercise already exists
        boolean exerciseExists = workout.getExercises().stream()
                .anyMatch(exercise -> exercise.getExerciseName().equalsIgnoreCase(exerciseDTO.getExerciseName()));
        if (exerciseExists) {
            throw new APIException("Exercise already exists!!");
        }

        // Map and save the new exercise
        Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
        exercise.setWorkout(workout);
        exercise.setUser(user); // ✅ Set the User object correctly

        Exercise savedExercise = exerciseRepository.save(exercise);

        return modelMapper.map(savedExercise, ExerciseDTO.class);
    }



    @Override
    public ApiResponse<ExerciseResponse> getAllExercises(String username, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        // Fetch only exercises belonging to the authenticated user
        Page<Exercise> pageExercises = exerciseRepository.findByUser(user, pageDetails);
        List<ExerciseDTO> exerciseDTOS = pageExercises.getContent().stream()
                .map(exercise -> modelMapper.map(exercise, ExerciseDTO.class))
                .toList();
        // Construct response
        ExerciseResponse exerciseResponse = new ExerciseResponse();
        exerciseResponse.setExerciseDTOS(exerciseDTOS);
        exerciseResponse.setPageNumber(pageExercises.getNumber());
        exerciseResponse.setPageSize(pageExercises.getSize());
        exerciseResponse.setTotalElements(pageExercises.getTotalElements());
        exerciseResponse.setTotalPages(pageExercises.getTotalPages());
        exerciseResponse.setLastPage(pageExercises.isLast());

        return new ApiResponse<>("Successfully retrieved all exercises", HttpStatus.OK.value(), exerciseResponse);
    }



    @Override
    public ExerciseResponse searchByWorkout(String username, Long workoutId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        // Validate user existence
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));
        // Validate workout existence
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout", "workoutId", workoutId));
        // Default sorting field if sortBy is invalid
        List<String> validSortFields = List.of("exerciseName", "repetitions", "duration"); // Ensure these fields exist in Exercise
        if (!validSortFields.contains(sortBy)) {
            sortBy = "exerciseName"; // Default field
        }
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Exercise> pageExercises = exerciseRepository.findByWorkout(workout, pageDetails);
        if (pageExercises.isEmpty()) {
            throw new APIException(workout.getTitle() + " workout does not have any exercises.");
        }
        List<ExerciseDTO> exerciseDTOS = pageExercises.getContent()
                .stream()
                .map(exercise -> modelMapper.map(exercise, ExerciseDTO.class))
                .toList();
        ExerciseResponse exerciseResponse = new ExerciseResponse();
        exerciseResponse.setExerciseDTOS(exerciseDTOS);
        exerciseResponse.setPageNumber(pageExercises.getNumber());
        exerciseResponse.setPageSize(pageExercises.getSize());
        exerciseResponse.setTotalElements(pageExercises.getTotalElements());
        exerciseResponse.setTotalPages(pageExercises.getTotalPages());
        exerciseResponse.setLastPage(pageExercises.isLast());

        return exerciseResponse;
    }

    @Override
    public ExerciseDTO getExerciseById(String username, Long exerciseId) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "exerciseId", exerciseId));

        if (!exercise.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("You can only access your own exercise");
        }
        return modelMapper.map(exercise, ExerciseDTO.class);

    }

    @Override
    public ExerciseDTO updateExercise(String username, Long exerciseId, ExerciseDTO exerciseDTO) {
        Exercise exerciseFromDb = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "exerciseId", exerciseId));

        exerciseFromDb.setExerciseName(exerciseDTO.getExerciseName());
        exerciseFromDb.setSetsExercises(exerciseDTO.getSetsExercises());
        exerciseFromDb.setExercisesRepetitions(exerciseDTO.getExercisesRepetitions());
        exerciseFromDb.setCompleted(exerciseDTO.getCompleted());
        exerciseFromDb.setCreatedAt(exerciseDTO.getCreatedAt());

        Exercise savedExercise = exerciseRepository.save(exerciseFromDb);

        return new ApiResponse<>("Updated Successfully",HttpStatus.OK.value(), modelMapper.map(savedExercise, ExerciseDTO.class)).getData();
    }


//    @Override
//    public ExerciseDTO deleteExercise(Long exerciseId) {
//
//        Exercise exercise = exerciseRepository.findById(exerciseId)
//                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "exerciseId", exerciseId));
//
//        exerciseRepository.delete(exercise);
//        return modelMapper.map(exercise, ExerciseDTO.class);
//
//    }


}






