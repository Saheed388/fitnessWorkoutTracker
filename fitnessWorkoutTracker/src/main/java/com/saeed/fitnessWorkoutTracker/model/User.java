package com.saeed.fitnessWorkoutTracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",  // Renamed from "user" to "users" (Avoid reserved keywords)
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName"), // Fixed unique constraint
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 2, message = "User name must contain at least 2 characters")
    private String userName;  // Ensure it matches unique constraint

    @Email
    private String email;

    private String password;

    private LocalDate createdAt; // Changed from String to LocalDate

    @ToString.Exclude
    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, // Added REMOVE
            orphanRemoval = true)
    private Set<Workouts> workouts;


    @Getter
    @Setter
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<WorkoutNotes> workoutNotes = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<WorkoutReports> workoutReports = new ArrayList<>();
}
