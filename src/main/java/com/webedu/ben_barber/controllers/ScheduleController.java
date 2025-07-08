package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/available-times")
    public ResponseEntity<List<LocalTime>> findAvailableTimes(@RequestParam Long barberId,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        if (date.isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().build();
        }

        List<LocalTime> times = scheduleService.findAvailableTimes(barberId, date);
        return ResponseEntity.ok(times);
    }
}