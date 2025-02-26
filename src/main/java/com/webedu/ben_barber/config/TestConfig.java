package com.webedu.ben_barber.config;

import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "Eduardo Benjamin", "eduardo.b@gmail.com", "1234");
        User u2 = new User(null, "Maria Eduarda", "maria.eduarda@gmail.com", "4321");

        userRepository.saveAll(Arrays.asList(u1, u2));
    }
}
