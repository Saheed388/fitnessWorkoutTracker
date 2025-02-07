package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.Workout;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Workout findByTitle(@NotBlank @Size(min = 5, message = "title must contain at least 5 characters") String title);
}
