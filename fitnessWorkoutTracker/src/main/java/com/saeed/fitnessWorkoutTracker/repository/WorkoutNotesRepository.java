package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.WorkoutNotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutNotesRepository extends JpaRepository<WorkoutNotes, Long> {
}
