package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Barber;
import com.webedu.ben_barber.entities.ScheduleHours;
import com.webedu.ben_barber.repositories.BarberRepository;
import com.webedu.ben_barber.repositories.HoursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class HoursGeneratorService {

    @Autowired
    private HoursRepository hoursRepository;

    @Autowired
    private BarberRepository barberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartupGenerateHoursAvailable() {
        log.info("Application started – generating available hours.");
        generateAllBarbersHours();
    }

    @Scheduled(cron = "0 0 0 1 * ?", zone = "America/Sao_Paulo")
    public void scheduledGenerateHoursAvailable() {
        log.info("Monthly schedule triggered – generating available hours.");
        generateAllBarbersHours();
    }

    private void generateAllBarbersHours() {
        LocalDateTime init = LocalDateTime.now();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(18, 0);

        List<Barber> barbers = barberRepository.findAll();
        for (Barber barber : barbers) {
            generateHoursForBarber(barber, init, start, end);
        }
    }

    public void generateHoursForBarber(Barber barber, LocalDateTime init, LocalTime start, LocalTime end) {

        Set<DayOfWeek> workingDays = EnumSet.of(
                DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
        );

        List<ScheduleHours> schedules = new ArrayList<>();

        YearMonth month = YearMonth.from(init);
        LocalDateTime currentDay = init;

        log.info("Generating hours available for barber: {}", barber.getId());
        for (int i = 0; i < month.lengthOfMonth(); i++) {
            if (workingDays.contains(currentDay.getDayOfWeek())) {
                LocalDateTime current = LocalDateTime.of(currentDay.toLocalDate(), start);

                while (current.toLocalTime().isBefore(end)) {
                    ScheduleHours schedule = new ScheduleHours(current.toLocalDate(), current.toLocalTime());
                    schedule.setBarber(barber);
                    schedules.add(schedule);

                    current = current.plusMinutes(30);
                }
            }
            currentDay = currentDay.plusDays(1);
        }
        hoursRepository.saveAll(schedules);
        log.info("Generated hours available for barber: {}", barber.getId());
    }
}
