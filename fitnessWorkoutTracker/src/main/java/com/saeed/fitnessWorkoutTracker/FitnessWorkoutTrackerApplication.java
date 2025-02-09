package com.saeed.fitnessWorkoutTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.saeed.fitnessWorkoutTracker.model")  // Ensure this is correct
@EnableJpaRepositories("com.saeed.fitnessWorkoutTracker.repository")
public class FitnessWorkoutTrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(FitnessWorkoutTrackerApplication.class, args);
	}
}
