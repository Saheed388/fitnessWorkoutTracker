package com.saeed.fitnessWorkoutTracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutReportResponse {

    private List<WorkoutReportDTO> workoutReportDTOS;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;

}
