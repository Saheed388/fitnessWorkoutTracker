package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.User;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.model.WorkoutNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutNoteRepository extends JpaRepository<WorkoutNote, Long> {

    Page<WorkoutNote> findByUser(User user, Pageable pageDetails);

    Page<WorkoutNote> findByWorkout(Workout workout, Pageable pageDetails);
}
