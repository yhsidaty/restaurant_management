package com.restaurant.config;

import com.restaurant.model.Role;
import com.restaurant.model.User;
import com.restaurant.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByUsername("manager").isEmpty()) {

            User manager = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("manager123"))
                    .fullName("Restaurant Manager")
                    .role(Role.MANAGER)
                    .active(true)
                    .build();

            userRepository.save(manager);

            System.out.println("✅ Manager account created!");
            System.out.println("Username: manager");
            System.out.println("Password: manager123");
        }
    }
}