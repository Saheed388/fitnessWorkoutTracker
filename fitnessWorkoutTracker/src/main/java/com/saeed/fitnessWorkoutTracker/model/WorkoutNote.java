package com.saeed.fitnessWorkoutTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "workout_notes")

public class WorkoutNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workoutNoteId;

    @Lob
    private String note;

    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}



