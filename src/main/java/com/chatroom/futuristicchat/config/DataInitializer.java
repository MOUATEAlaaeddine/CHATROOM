package com.chatroom.futuristicchat.config;

import com.chatroom.futuristicchat.entity.User;
import com.chatroom.futuristicchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Admin if not exists
        // Check if admin exists
        User admin = userRepository.findByUsername("admin");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
        }
        // Always force update/set these fields to ensure consistency
        admin.setPasswordHash(passwordEncoder.encode("admin123")); // Default password
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);
        System.out.println("Default Admin ensured: admin / admin123");
    }
}
