package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.Exercise;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {



    Page<Exercise> findByWorkoutOrderByExercisesRepetitionsAsc(Workout workout, Pageable pageDetails);
}
