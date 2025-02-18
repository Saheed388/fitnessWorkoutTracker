package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.User;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Workout findByTitle(@NotBlank @Size(min = 5, message = "title must contain at least 5 characters") String title);

    Workout findByTitleAndUser(String title, User user);

    Page<Workout> findByUser(User user, Pageable pageDetails);

    List<Workout> findByUser_UserId(Long userId);
}
