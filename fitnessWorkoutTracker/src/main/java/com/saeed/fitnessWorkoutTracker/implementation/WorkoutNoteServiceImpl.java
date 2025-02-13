package com.saeed.fitnessWorkoutTracker.implementation;

import com.saeed.fitnessWorkoutTracker.exception.APIException;
import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.exception.ResourceNotFoundException;
import com.saeed.fitnessWorkoutTracker.model.Exercise;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.model.WorkoutNote;
import com.saeed.fitnessWorkoutTracker.payload.ExerciseDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteDTO;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutNoteResponse;
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


    @Override
    public WorkoutNoteDTO addWorkoutNote(Long workoutId, WorkoutNoteDTO workoutNoteDTO) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Workout", "workoutId", workoutId));

        boolean isWorkoutNoteNotPresent = true;

        List<WorkoutNote> workoutNotes = workout.getWorkoutNotes();
        for (WorkoutNote value : workoutNotes) {
            if (value.getNote().equals(workoutNoteDTO.getNote())) {
                isWorkoutNoteNotPresent = false;
                break;
            }
        }

        if (isWorkoutNoteNotPresent) {
            WorkoutNote workoutNote = modelMapper.map(workoutNoteDTO, WorkoutNote.class);
            workoutNote.setWorkout(workout);

            WorkoutNote savedWorkoutNote = workoutNoteRepository.save(workoutNote);
            return modelMapper.map(savedWorkoutNote, WorkoutNoteDTO.class);
        } else {
            throw new APIException("WorkoutNote already exist!!");
        }
    }


    @Override
    public ApiResponse<WorkoutNoteResponse> getAllWorkoutNote(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<WorkoutNote> pageWorkoutNotes = workoutNoteRepository.findAll(pageDetails);

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
    public WorkoutNoteResponse searchNoteByWorkout(Long workoutId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Workout", "workoutId", workoutId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<WorkoutNote> pageWorkoutNote = workoutNoteRepository.findByWorkoutOrderByWorkoutNoteIdAsc(workout, pageDetails);
        List<WorkoutNote> workoutNotes = pageWorkoutNote.getContent();

        if (workoutNotes.isEmpty()) {
            throw new APIException(workout.getWorkoutNotes() + " workout does not have any Note");
        }

        List<WorkoutNoteDTO> workoutNoteDTOS = workoutNotes.stream()
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
    public WorkoutNoteDTO getWorkoutNoteById(Long workoutNoteId) {
        return workoutNoteRepository.findById(workoutNoteId)
                .map(workoutNote -> modelMapper.map(workoutNote, WorkoutNoteDTO.class))
                .orElseThrow(()-> new ResourceNotFoundException("WorkoutNote", "workoutNoteId", workoutNoteId));
    }

    @Override
    public WorkoutNoteDTO updateWorkoutNote(Long workoutNoteId, WorkoutNoteDTO workoutNoteDTO) {
        WorkoutNote workoutNoteFromDb = workoutNoteRepository.findById(workoutNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutNote", "workoutNoteId", workoutNoteId));

        workoutNoteFromDb.setNote(workoutNoteDTO.getNote());
        workoutNoteFromDb.setCreatedAt(workoutNoteDTO.getCreatedAt());

        WorkoutNote savedWorkoutNote = workoutNoteRepository.save(workoutNoteFromDb);

        return new ApiResponse<>("Updated Successfully", HttpStatus.OK.value(),modelMapper.map(savedWorkoutNote, WorkoutNoteDTO.class)).getData();
    }

    @Override
    public WorkoutNoteDTO deleteworkoutNote(Long workoutNoteId) {
        WorkoutNote workoutNote = workoutNoteRepository.findById(workoutNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutNote", "workoutNoteId", workoutNoteId));

        workoutNoteRepository.delete(workoutNote);
        return modelMapper.map(workoutNote, WorkoutNoteDTO.class);
    }
}




