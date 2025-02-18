package com.saeed.fitnessWorkoutTracker.service;

import com.saeed.fitnessWorkoutTracker.payload.WorkoutReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutReportService {
    WorkoutReportDTO generateWorkoutReport(Long userId);
    List<WorkoutReportDTO> getWorkoutReportsByUser(Long userId);
    List<WorkoutReportDTO> getWorkoutReportsByDate(LocalDate date);
}