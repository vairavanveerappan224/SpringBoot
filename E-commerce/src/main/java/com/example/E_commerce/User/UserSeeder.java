package com.example.E_commerce.User;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepo.existsByEmail("admin@example.com")) {

            UserEntity admin = new UserEntity();
            admin.setName("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setGender("Male");
            admin.setPhoneNo("9999999999");
            admin.setAddress("Head Office");
            admin.setUserType(3);

            userRepo.save(admin);
        }

        if (!userRepo.existsByEmail("user@example.com")) {

            UserEntity user = new UserEntity();
            user.setName("User");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setGender("Female");
            user.setPhoneNo("8888888888");
            user.setAddress("Chennai");
            user.setUserType(4);

            userRepo.save(user);
        }
    }
}