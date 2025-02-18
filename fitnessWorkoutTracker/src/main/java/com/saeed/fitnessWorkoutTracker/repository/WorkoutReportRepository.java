package com.saeed.fitnessWorkoutTracker.repository;

import com.saeed.fitnessWorkoutTracker.model.WorkoutReport;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public interface WorkoutReportRepository extends JpaRepository<WorkoutReport, Long> {
    List<WorkoutReport> findByUser_UserId(Long userId);
    List<WorkoutReport> findByDate(LocalDate date);



}