package com.saeed.fitnessWorkoutTracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exercisesId;

    @NotBlank
    @Size(min = 5, message = "Category  must contain atleast 5 characters")
    private String exerciseName;

    private Long setsExercises;
    private Long exercisesRepetitions;
    private Boolean completed;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workouts;

}

