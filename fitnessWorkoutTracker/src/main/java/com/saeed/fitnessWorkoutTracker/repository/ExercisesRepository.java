package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.Exercises;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercisesRepository extends JpaRepository<Exercises, Long> {
}
