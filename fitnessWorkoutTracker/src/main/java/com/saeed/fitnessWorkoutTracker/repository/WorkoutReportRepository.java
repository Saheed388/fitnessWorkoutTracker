package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.WorkoutReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutReportRepository extends JpaRepository<WorkoutReport, Long> {
}
