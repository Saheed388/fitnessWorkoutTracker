package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.WorkoutNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutNoteRepository extends JpaRepository<WorkoutNote, Long> {
}
