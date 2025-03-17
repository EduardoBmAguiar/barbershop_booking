package com.webedu.ben_barber.config;

import com.webedu.ben_barber.entities.Agendate;
import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.enums.AgendateStatus;
import com.webedu.ben_barber.repositories.AgendateRepository;
import com.webedu.ben_barber.repositories.OptionRepository;
import com.webedu.ben_barber.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AgendateRepository agendateRepository;

    @Autowired
    OptionRepository optionRepository;

    @Override
    public void run(String... args) {
        User u1 = new User(null, "Eduardo Benjamin", "eduardo.b@gmail.com", "1234");
        User u2 = new User(null, "Maria Eduarda", "maria.eduarda@gmail.com", "4321");

        Option o1 = new Option(null, "Corte", BigDecimal.valueOf(30));
        Option o2 = new Option(null, "Corte_barba", BigDecimal.valueOf(50));

        Agendate a1 = new Agendate(null, LocalDateTime.parse("2025-02-28T19:00"), AgendateStatus.MARKED, u1, o1);
        Agendate a2 = new Agendate(null, LocalDateTime.parse("2025-01-20T19:00"), AgendateStatus.PAYED, u2, o2);

        userRepository.saveAll(Arrays.asList(u1, u2));
        optionRepository.saveAll(Arrays.asList(o1, o2));
        agendateRepository.saveAll(Arrays.asList(a1, a2));

    }
}
