package com.onboarding.config;

import com.onboarding.model.Role;
import com.onboarding.model.User;
import com.onboarding.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Development helper: creates demo login accounts when the app starts, because the in-memory
 * database is empty after every restart. Runs only when app.seed-demo-data=true.
 *
 * These passwords are public in the source code. Turn this off (and delete the accounts)
 * before any real deployment.
 */
@Component
@ConditionalOnProperty(name = "app.seed-demo-data", havingValue = "true")
public class DemoDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DemoDataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createIfMissing("admin", "admin@example.com", "Admin123!", Role.ADMIN);
        createIfMissing("manager", "manager@example.com", "Manager123!", Role.MANAGER);
        createIfMissing("employee", "employee@example.com", "Employee123!", Role.EMPLOYEE);
    }

    private void createIfMissing(String username, String email, String password, Role role) {
        if (!userRepository.existsByUsername(username)) {
            userRepository.save(new User(username, email, passwordEncoder.encode(password), role));
        }
    }
}
