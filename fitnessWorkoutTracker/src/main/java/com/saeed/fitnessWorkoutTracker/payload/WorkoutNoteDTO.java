package com.saeed.fitnessWorkoutTracker.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutNoteDTO {
    private Long workoutNoteId;

    private String note;

    private LocalDate createdAt;

}
