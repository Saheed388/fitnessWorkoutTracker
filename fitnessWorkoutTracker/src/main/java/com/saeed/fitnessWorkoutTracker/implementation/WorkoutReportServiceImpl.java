package com.saeed.fitnessWorkoutTracker.implementation;


import com.saeed.fitnessWorkoutTracker.model.User;
import com.saeed.fitnessWorkoutTracker.model.Workout;
import com.saeed.fitnessWorkoutTracker.model.WorkoutReport;
import com.saeed.fitnessWorkoutTracker.payload.WorkoutReportDTO;
import com.saeed.fitnessWorkoutTracker.repository.UserRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutReportRepository;
import com.saeed.fitnessWorkoutTracker.repository.WorkoutRepository;
import com.saeed.fitnessWorkoutTracker.service.WorkoutReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutReportServiceImpl implements WorkoutReportService {

    @Autowired
     WorkoutReportRepository workoutReportRepository;

    @Autowired
     WorkoutRepository workoutRepository;

    @Autowired
     UserRepository userRepository;

    @Override
    public WorkoutReportDTO generateWorkoutReport(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Workout> workouts = workoutRepository.findByUser_UserId(userId);
        long totalWorkouts = workouts.size();
        long completedWorkouts = workouts.stream().filter(Workout::isCompleted).count();
        double completionPercentage = (totalWorkouts > 0) ? (double) completedWorkouts / totalWorkouts * 100 : 0;

        WorkoutReport report = new WorkoutReport();
        report.setTotalWorkouts(totalWorkouts);
        report.setCompletedWorkouts(completedWorkouts);
        report.setCompletionPercentage(completionPercentage);
        report.setDate(LocalDate.now());
        report.setUser(user);

        workoutReportRepository.save(report);

        return mapToDTO(report);
    }

    @Override
    public List<WorkoutReportDTO> getWorkoutReportsByUser(Long userId) {
        return workoutReportRepository.findByUser_UserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutReportDTO> getWorkoutReportsByDate(LocalDate date) {
        return workoutReportRepository.findByDate(date).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private WorkoutReportDTO mapToDTO(WorkoutReport report) {
        return new WorkoutReportDTO(
                report.getTotalWorkouts(),
                report.getCompletedWorkouts(),
                report.getCompletionPercentage(),
                report.getDate()
        );
    }
}