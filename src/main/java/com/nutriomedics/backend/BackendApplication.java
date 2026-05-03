package com.nutriomedics.backend;

import com.nutriomedics.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // ✅ Runs ONLY in development (prevents production issues)
    @Bean
    @Profile("dev")
    public CommandLineRunner initAdmin(UserService userService) {
        return args -> {

            String email = "admin@nutriomedics.com";

            if (userService.findByEmail(email).isEmpty()) {
                userService.registerAdmin(
                        "Super Admin",
                        email,
                        "admin123");

                System.out.println("✅ DEV ADMIN CREATED: " + email + " / admin123");
            } else {
                System.out.println("ℹ️ Admin already exists: " + email);
            }
        };
    }
}