package com.webedu.ben_barber.config;

import com.webedu.ben_barber.entities.*;
import com.webedu.ben_barber.enums.AgendateStatus;
import com.webedu.ben_barber.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AgendateRepository agendateRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    BarberRepository barberRepository;

    @Autowired
    HoursRepository hoursRepository;

    @Override
    public void run(String... args) {
        Client u1 = new Client(null, "Eduardo Benjamin", "eduardo.b@gmail.com", "1234");
        Client u2 = new Client(null, "Maria Eduarda", "maria.eduarda@gmail.com", "4321");

        Barber b1 = new Barber(null, "Edu do corte", "eduardo.barber@gmail.com", "1234");
        Barber b2 = new Barber(null, "Maria do corte", "maria.barber@gmail.com", "4321");

        Option o1 = new Option(null, "Corte", BigDecimal.valueOf(30));
        Option o2 = new Option(null, "Corte_barba", BigDecimal.valueOf(50));

        ScheduleHours h1 = new ScheduleHours(LocalDate.of(2025, 4, 10), LocalTime.of(9, 30));
        ScheduleHours h2 = new ScheduleHours(LocalDate.of(2025, 4, 15), LocalTime.of(15, 0));
        hoursRepository.saveAll(Arrays.asList(h1, h2));

        Agendate a1 = new Agendate(null, h1, AgendateStatus.MARKED, u1, b2, o1);
        Agendate a2 = new Agendate(null, h2, AgendateStatus.PAYED, u2, b1, o2);


        barberRepository.saveAll(Arrays.asList(b1, b2));
        clientRepository.saveAll(Arrays.asList(u1, u2));
        optionRepository.saveAll(Arrays.asList(o1, o2));
        agendateRepository.saveAll(Arrays.asList(a1, a2));

    }
}
