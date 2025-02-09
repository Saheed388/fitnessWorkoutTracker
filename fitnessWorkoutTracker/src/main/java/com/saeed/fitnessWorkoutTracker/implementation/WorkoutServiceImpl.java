package com.saeed.fitnessWorkoutTracker.implementation;

import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ResourceNotFoundException;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutResponse;
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
     ModelMapper modelMapper;

    @Override
    public WorkoutResponse getAllWorkouts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Workout> workoutPage = workoutRepository.findAll(pageDetails);

        List<Workout> workouts = workoutPage.getContent();
        if (workouts.isEmpty())
            throw new APIException("No workout created till now.");

        List<WorkoutDTO> workoutDTOS = workouts.stream()
                .map(workout -> modelMapper.map(workout, WorkoutDTO.class))
                .toList();

        WorkoutResponse workoutResponse = new WorkoutResponse();
        workoutResponse.setWorkoutDTOS(workoutDTOS);
        workoutResponse.setPageNumber(workoutPage.getNumber());
        workoutResponse.setPageSize(workoutPage.getSize());
        workoutResponse.setTotalElements(workoutPage.getTotalElements());
        workoutResponse.setTotalPages(workoutPage.getTotalPages());
        workoutResponse.setLastPage(workoutPage.isLast());
        return workoutResponse;

    }

    @Override
    public WorkoutDTO createWorkout(WorkoutDTO workoutDTO) {
        Workout workout = modelMapper.map(workoutDTO, Workout.class);
        Workout workoutFromDb = workoutRepository.findByTitle(workout.getTitle());
        if (workoutFromDb != null)
            throw new APIException("Category with the name " + workout.getTitle() + " already exists !!!");
        Workout savedWorkout = workoutRepository.save(workout);
        return modelMapper.map(savedWorkout, WorkoutDTO.class);
    }

    @Override
    public WorkoutDTO getContentById(Long workoutId) {
        return workoutRepository.findById(workoutId)
                .map(workout -> modelMapper.map(workout, WorkoutDTO.class))
                .orElseThrow(()-> new ResourceNotFoundException("Workout", "workoutId", workoutId));
    }

    @Override
    public WorkoutDTO deleteWorkout(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(()-> new ResourceNotFoundException( "Workout", "workoutId", workoutId));
        workoutRepository.delete(workout);
        return modelMapper.map(workout, WorkoutDTO.class);

    }

    @Override
    public WorkoutDTO updateCategory(WorkoutDTO workoutDTO, Long workoutId) {
        Workout savedWorkout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout", "workoutId", workoutId));
        savedWorkout.setTitle(workoutDTO.getTitle());
        savedWorkout.setScheduled_date(workoutDTO.getScheduled_date());
        Workout updatedWorkout = workoutRepository.save(savedWorkout);

        return modelMapper.map(updatedWorkout, WorkoutDTO.class);
    }
}

