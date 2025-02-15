package com.saeed.fitnessWorkoutTracker.implementation;

import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.exception.ResourceNotFoundException;
import com.saeed.fitnessWorkoutTracker.model.User;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.model.WorkoutNote;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteResponse;
import com.saeed.fitnessWorkoutTracker.repository.UserRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutNoteRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutRepository;
import com.saeed.fitnessWorkoutTracker.service.WorkoutNoteService;
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
public class WorkoutNoteServiceImpl implements WorkoutNoteService {

    @Autowired
    WorkoutRepository workoutRepository;


    @Autowired
     ModelMapper modelMapper;

    @Autowired
    WorkoutNoteRepository workoutNoteRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public WorkoutNoteDTO addWorkoutNote(String username, Long workoutId, WorkoutNoteDTO workoutNoteDTO) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Workout", "workoutId", workoutId));
        if (!workout.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("You are not authorized to modify this workout.");
        }
        boolean workoutNoteExist = workout.getWorkoutNotes().stream()
                .anyMatch(workoutNote -> workoutNote.getWorkoutNoteId().equals(workoutNoteDTO.getWorkoutNoteId()));
        if(workoutNoteExist){
            throw new APIException("Workout note already exist");
        }
            WorkoutNote workoutNote = modelMapper.map(workoutNoteDTO, WorkoutNote.class);
            workoutNote.setWorkout(workout);
            workoutNote.setUser(user);
            WorkoutNote savedWorkoutNote = workoutNoteRepository.save(workoutNote);
            return modelMapper.map(savedWorkoutNote, WorkoutNoteDTO.class);

    }



    @Override
    public ApiResponse<WorkoutNoteResponse> getAllWorkoutNote(String username, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<WorkoutNote> pageWorkoutNotes = workoutNoteRepository.findByUser(user,pageDetails);

        List<WorkoutNote> workoutNotes = pageWorkoutNotes.getContent();

        List<WorkoutNoteDTO> workoutNoteDTOS = workoutNotes.stream()
                .map(workoutNote -> modelMapper.map(workoutNote, WorkoutNoteDTO.class))
                .toList();

        WorkoutNoteResponse workoutNoteResponse = new WorkoutNoteResponse();
        workoutNoteResponse.setWorkoutNoteDTOS(workoutNoteDTOS);
        workoutNoteResponse.setPageNumber(pageWorkoutNotes.getNumber());
        workoutNoteResponse.setPageSize(pageWorkoutNotes.getSize());
        workoutNoteResponse.setTotalElements(pageWorkoutNotes.getTotalElements());
        workoutNoteResponse.setTotalPages(pageWorkoutNotes.getTotalPages());
        workoutNoteResponse.setLastPage(pageWorkoutNotes.isLast());

        return new ApiResponse<>("Successfully retrieved all Workout Notes ", HttpStatus.OK.value(), workoutNoteResponse);
    }




    @Override
    public WorkoutNoteResponse searchNoteByWorkout(String username, Long workoutId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout", "workoutId", workoutId));

        // Ensure the workout belongs to the authenticated user
        if (!workout.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("Unauthorized access! You can only search notes for your own workout.");
        }

        // Ensure valid sort fields
        List<String> validSortFields = List.of("note", "createdAt");
        if (!validSortFields.contains(sortBy)) {
            sortBy = "createdAt"; // Default sorting field
        }

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<WorkoutNote> pageWorkoutNote = workoutNoteRepository.findByWorkout(workout, pageDetails);

        if (pageWorkoutNote.isEmpty()) {
            throw new APIException("Workout does not have any notes.");
        }

        List<WorkoutNoteDTO> workoutNoteDTOS = pageWorkoutNote.getContent().stream()
                .map(workoutNote -> modelMapper.map(workoutNote, WorkoutNoteDTO.class))
                .toList();

        WorkoutNoteResponse workoutNoteResponse = new WorkoutNoteResponse();
        workoutNoteResponse.setWorkoutNoteDTOS(workoutNoteDTOS);
        workoutNoteResponse.setPageNumber(pageWorkoutNote.getNumber());
        workoutNoteResponse.setPageSize(pageWorkoutNote.getSize());
        workoutNoteResponse.setTotalElements(pageWorkoutNote.getTotalElements());
        workoutNoteResponse.setTotalPages(pageWorkoutNote.getTotalPages());
        workoutNoteResponse.setLastPage(pageWorkoutNote.isLast());

        return workoutNoteResponse;
    }


    @Override
    public WorkoutNoteDTO getWorkoutNoteById(String username, Long workoutNoteId) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User not found with username: " + username));

        WorkoutNote workoutNote = workoutNoteRepository.findById(workoutNoteId)
                .orElseThrow(()-> new ResourceNotFoundException("WorkoutNote", "workoutNoteId", workoutNoteId));

        if (!workoutNote.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("You can only access your own workoutNote");
        }
        return  modelMapper.map(workoutNote, WorkoutNoteDTO.class);
    }


    @Override
    public WorkoutNoteDTO updateWorkoutNote(String username, Long workoutNoteId, WorkoutNoteDTO workoutNoteDTO){
    WorkoutNote workoutNoteFromDb = workoutNoteRepository.findById(workoutNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutNote", "workoutNoteId", workoutNoteId));

        User user = userRepository.findByUserName(username)
                .orElseThrow(()-> new APIException("User not found with username:" + username));

        if (!workoutNoteFromDb.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("You can only update your own exercise !");
        }

        workoutNoteFromDb.setNote(workoutNoteDTO.getNote());
        workoutNoteFromDb.setCreatedAt(workoutNoteDTO.getCreatedAt());

        WorkoutNote savedWorkoutNote = workoutNoteRepository.save(workoutNoteFromDb);

        return new ApiResponse<>("Updated Successfully", HttpStatus.OK.value(),modelMapper.map(savedWorkoutNote, WorkoutNoteDTO.class)).getData();
    }




    @Override
    public WorkoutNoteDTO deleteWorkoutNote(String username, Long workoutNoteId) {
        WorkoutNote workoutNoteFromDb = workoutNoteRepository.findById(workoutNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutNote", "workoutNoteId", workoutNoteId));

        User user = userRepository.findByUserName(username)
                .orElseThrow(()-> new APIException("User not found with username:" + username));
        if (!workoutNoteFromDb.getUser().getUserId().equals(user.getUserId())) {
            throw new APIException("You can only update your own workout note !");
        }

        workoutNoteRepository.delete(workoutNoteFromDb);
        return modelMapper.map(workoutNoteFromDb, WorkoutNoteDTO.class);
    }



}




