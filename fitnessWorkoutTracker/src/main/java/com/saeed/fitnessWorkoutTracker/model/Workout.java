package com.saeed.fitnessWorkoutTracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workoutId;


    @NotBlank
    @Size(min = 5, message = "title must contain at least 5 characters")
    private String title;

    private LocalDate scheduled_date;
    private LocalDate created_at;



    @ManyToOne
    @JoinColumn(name = "userId") // Changed to match User entity's primary key
    private User user;


    @Getter
    @Setter
    @OneToMany(mappedBy = "workouts",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "workouts",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<WorkoutNote> workoutNotes = new ArrayList<>();
}

