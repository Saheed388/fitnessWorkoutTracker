package com.saeed.fitnessWorkoutTracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Size(min = 5, message = "Title must contain at least 5 characters")
    private String title;

    private LocalDate scheduled_date;
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // ✅ Proper foreign key mapping
    private User user;

    @OneToMany(mappedBy = "workout", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    @OneToMany(mappedBy = "workout", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<WorkoutNote> workoutNotes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }
}
