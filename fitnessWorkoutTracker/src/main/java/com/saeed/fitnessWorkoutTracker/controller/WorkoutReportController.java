package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.payload.WorkoutReportDTO;
import com.saeed.fitnessWorkoutTracker.service.WorkoutReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutReportController {

    @Autowired
    private WorkoutReportService workoutReportService;

    @PostMapping("/workout-reports/generate/{userId}")
    public ResponseEntity<WorkoutReportDTO> generateWorkoutReport(@PathVariable Long userId) {
        WorkoutReportDTO report = workoutReportService.generateWorkoutReport(userId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutReportDTO>> getWorkoutReportsByUser(@PathVariable Long userId) {
        List<WorkoutReportDTO> reports = workoutReportService.getWorkoutReportsByUser(userId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/workout-reports/date")
    public ResponseEntity<List<WorkoutReportDTO>> getWorkoutReportsByDate(@RequestParam LocalDate date) {
        List<WorkoutReportDTO> reports = workoutReportService.getWorkoutReportsByDate(date);
        return ResponseEntity.ok(reports);
    }
}