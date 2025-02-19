package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutReportDTO;
import com.saeed.fitnessWorkoutTracker.service.WorkoutReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<WorkoutReportDTO>> generateWorkoutReport(@PathVariable Long userId) {
        WorkoutReportDTO report = workoutReportService.generateWorkoutReport(userId);
        return new ResponseEntity<>(new ApiResponse<>("Report Generated Successfully",HttpStatus.OK.value(), report), HttpStatus.OK);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<WorkoutReportDTO>>> getWorkoutReportsByUser(@PathVariable Long userId) {
        List<WorkoutReportDTO> reports = workoutReportService.getWorkoutReportsByUser(userId);
        return new ResponseEntity<>(new ApiResponse<>("User Generated Successfully",HttpStatus.OK.value(), reports), HttpStatus.OK);
    }

    @GetMapping("/workout-reports/date")
    public ResponseEntity<ApiResponse<List<WorkoutReportDTO>>> getWorkoutReportsByDate(@RequestParam LocalDate date) {
        List<WorkoutReportDTO> reports = workoutReportService.getWorkoutReportsByDate(date);
        return new ResponseEntity<>(new ApiResponse<>("Date Generated Successfully",HttpStatus.OK.value(), reports), HttpStatus.OK);
    }
}

