package com.microtech.smartshop.config;

import com.microtech.smartshop.enums.Role;
import com.microtech.smartshop.model.User;
import com.microtech.smartshop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Slf4j
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository , BCryptPasswordEncoder passwordEncoder) {
        return args -> {

            if (!userRepository.existsByUsername("admin")) {

                String hashpassword = passwordEncoder.encode("admin123");

                User admin = User.builder()
                        .username("admin")
                        .password(hashpassword)
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);

                log.info(" Admin account created: admin / admin123");
            }
        };
    }
}
