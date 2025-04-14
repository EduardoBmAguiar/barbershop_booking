package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.ScheduleHours;
import com.webedu.ben_barber.repositories.HoursRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

@Slf4j
@Service
public class HoursGeneratorService {

    @Autowired
    public HoursRepository hoursRepository;

    @Scheduled(cron = "0 0 0 1 * ?", zone = "America/Sao_Paulo")
    @PostConstruct
    public void generatorHoursAvailable() {
        LocalDateTime init = LocalDateTime.now();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(18, 0);

        HoursGenerator(init, start, end);
    }

    public void HoursGenerator(LocalDateTime init, LocalTime start, LocalTime end) {

        log.info("Generating hours available");
        for (int i = 0; i <= YearMonth.from(init).lengthOfMonth(); i++) {
            if (init.getDayOfWeek() != DayOfWeek.MONDAY && init.getDayOfWeek() != DayOfWeek.SUNDAY) {
                LocalDateTime current = LocalDateTime.of(init.toLocalDate(), start);

                while (current.toLocalTime().isBefore(end)) {
                    hoursRepository.save(new ScheduleHours(current.toLocalDate(), LocalTime.of(current.getHour(), current.getMinute())));
                    current = current.plusMinutes(30);
                }
            }
            init = init.plusDays(1);
        }
        log.info("Generated hours available");
    }
}
