package com.saeed.fitnessWorkoutTracker.implementation;

import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ResourceNotFoundException;
import com.saeed.fitnessWorkoutTracker.model.User;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutResponse;
import com.saeed.fitnessWorkoutTracker.repository.UserRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutRepository;
import com.saeed.fitnessWorkoutTracker.service.WorkoutService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    ModelMapper modelMapper;

//    @Override
//    public WorkoutResponse getAllWorkouts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
//        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
//                ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
//        Page<Workout> workoutPage = workoutRepository.findAll(pageDetails);
//
//        List<Workout> workouts = workoutPage.getContent();
//        if (workouts.isEmpty())
//            throw new APIException("No workout created till now.");
//
//        List<WorkoutDTO> workoutDTOS = workouts.stream()
//                .map(workout -> modelMapper.map(workout, WorkoutDTO.class))
//                .toList();
//
//        WorkoutResponse workoutResponse = new WorkoutResponse();
//        workoutResponse.setWorkoutDTOS(workoutDTOS);
//        workoutResponse.setPageNumber(workoutPage.getNumber());
//        workoutResponse.setPageSize(workoutPage.getSize());
//        workoutResponse.setTotalElements(workoutPage.getTotalElements());
//        workoutResponse.setTotalPages(workoutPage.getTotalPages());
//        workoutResponse.setLastPage(workoutPage.isLast());
//        return workoutResponse;
//
//    }


//    @Override
//    public WorkoutDTO getContentById(Long workoutId) {
//        return workoutRepository.findById(workoutId)
//                .map(workout -> modelMapper.map(workout, WorkoutDTO.class))
//                .orElseThrow(() -> new ResourceNotFoundException("Workout", "workoutId", workoutId));
//    }

//    @Override
//    public WorkoutDTO deleteWorkout(Long workoutId) {
//        Workout workout = workoutRepository.findById(workoutId)
//                .orElseThrow(() -> new ResourceNotFoundException("Workout", "workoutId", workoutId));
//        workoutRepository.delete(workout);
//        return modelMapper.map(workout, WorkoutDTO.class);
//
//    }

    @Override
    public WorkoutDTO updateWorkout(WorkoutDTO workoutDTO, Long workoutId, String username) {
        // Fetch the workout from the database
        Workout savedWorkout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout", "workoutId", workoutId));

        // Fetch the authenticated user
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));

        // Ensure only the owner can update the workout
        if (!savedWorkout.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("You can only update your own workouts!");
        }

        // Update only allowed fields
        savedWorkout.setTitle(workoutDTO.getTitle());
        savedWorkout.setScheduled_date(workoutDTO.getScheduled_date());

        // Save and return updated workout
        Workout updatedWorkout = workoutRepository.save(savedWorkout);
        WorkoutDTO updatedWorkoutDTO = modelMapper.map(updatedWorkout, WorkoutDTO.class);
        updatedWorkoutDTO.setUserId(user.getUserId()); // ✅ Ensure userId is included

        return updatedWorkoutDTO;
    }


    @Override
    public WorkoutDTO createWorkout(WorkoutDTO workoutDTO, String username) {
        // Find user
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));

        // Convert DTO to Entity
        Workout workout = modelMapper.map(workoutDTO, Workout.class);
        workout.setUser(user); // ✅ Assign user before saving

        // Save workout
        Workout savedWorkout = workoutRepository.save(workout);

        // Convert back to DTO
        WorkoutDTO savedWorkoutDTO = modelMapper.map(savedWorkout, WorkoutDTO.class);
        savedWorkoutDTO.setUserId(user.getUserId()); // ✅ Assign user ID in DTO

        return savedWorkoutDTO;
    }

}


