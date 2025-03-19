package com.webedu.ben_barber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HoursAvailable {

    @Autowired
    public List<LocalDateTime> hoursAvailable;

    public HoursAvailable() {
        this.hoursAvailable = new ArrayList<>();
        this.generatorHoursAvailable();
    }

    public void generatorHoursAvailable() {
        LocalDateTime init = LocalDateTime.now();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(18, 0);

        HoursGenerator(init, start, end);
    }

    public void HoursGenerator(LocalDateTime init, LocalTime start, LocalTime end) {

        for (int i = 0; i <= 14; i++) {
            if (init.getDayOfWeek() != DayOfWeek.MONDAY && init.getDayOfWeek() != DayOfWeek.SUNDAY) {
                LocalDateTime current = LocalDateTime.of(init.toLocalDate(), start);

                while (current.toLocalTime().isBefore(end)) {
                    hoursAvailable.add(current);
                    current = current.plusMinutes(30);
                }
            }
            init = init.plusDays(1);
        }
    }
}
