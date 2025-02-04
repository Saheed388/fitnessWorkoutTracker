package com.saeed.fitnessWorkoutTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "workout_notes")

public class WorkoutNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workoutNoteId;

    @Lob
    private String note;

    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workouts workouts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}



