package com.webedu.ben_barber.config;

import com.webedu.ben_barber.entities.Agendate;
import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.enums.AgendateStatus;
import com.webedu.ben_barber.repositories.AgendateRepository;
import com.webedu.ben_barber.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AgendateRepository agendateRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "Eduardo Benjamin", "eduardo.b@gmail.com", "1234");
        User u2 = new User(null, "Maria Eduarda", "maria.eduarda@gmail.com", "4321");

        Agendate a1 = new Agendate(null, Instant.parse("2025-02-28T19:00:00Z"), AgendateStatus.MARKED, u1);
        Agendate a2 = new Agendate(null, Instant.parse("2025-01-20T19:00:00Z"), AgendateStatus.PAYED, u2);

        userRepository.saveAll(Arrays.asList(u1, u2));
        agendateRepository.saveAll(Arrays.asList(a1, a2));

    }
}
