package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Appointment;
import com.webedu.ben_barber.entities.ScheduleOverride;
import com.webedu.ben_barber.entities.TimeBlock;
import com.webedu.ben_barber.entities.WorkingHours;
import com.webedu.ben_barber.repositories.AppointmentRepository;
import com.webedu.ben_barber.repositories.ScheduleOverrideRepository;
import com.webedu.ben_barber.repositories.TimeBlockRepository;
import com.webedu.ben_barber.repositories.WorkingHoursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private TimeBlockRepository timeBlockRepository;

    @Autowired
    private ScheduleOverrideRepository scheduleOverrideRepository;

    public List<LocalTime> findAvailableTimes(Long barberId, LocalDate date) {
        LocalTime effectiveStartTime;
        LocalTime effectiveEndTime;

        Optional<ScheduleOverride> overrideOpt = scheduleOverrideRepository.findByBarberIdAndOverrideDate(barberId, date);
        if (overrideOpt.isPresent()) {
            log.info("Override schedule found for barber {} on date {}", barberId, date);
            ScheduleOverride override = overrideOpt.get();
            effectiveStartTime = override.getStartTime();
            effectiveEndTime = override.getEndTime();
        } else {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            Optional<WorkingHours> workingHoursOpt = workingHoursRepository.findByBarberIdAndDayOfWeek(barberId, dayOfWeek);
            if (workingHoursOpt.isEmpty()) {
                log.info("No working hours or override found for barber {} on {}", barberId, date);
                return Collections.emptyList();
            }
            WorkingHours workingHours = workingHoursOpt.get();
            effectiveStartTime = workingHours.getStartTime();
            effectiveEndTime = workingHours.getEndTime();
        }

        List<TimeBlock> blocks = timeBlockRepository.findAllActiveBySpecificDate(date, barberId);
        if (blocks.stream().anyMatch(b -> b.getStartTime() == null && b.getEndTime() == null)) {
            log.info("Full day block found for date {}.", date);
            return Collections.emptyList();
        }

        List<Appointment> activeAppointments = appointmentRepository.findActiveAppointmentsByBarberAndDate(barberId, date);
        Set<LocalTime> bookedTimes = activeAppointments.stream()
                .map(a -> a.getScheduleHours().getHourTime())
                .collect(Collectors.toSet());

        List<LocalTime> availableTimes = new ArrayList<>();
        final int slotDurationInMinutes = 30;

        LocalTime currentSlot = effectiveStartTime;
        while (currentSlot.isBefore(effectiveEndTime)) {

            final LocalTime slotToCheck = currentSlot;

            boolean isBooked = bookedTimes.contains(slotToCheck);

            boolean isBlocked = blocks.stream().anyMatch(b ->
                    b.getStartTime() != null && !slotToCheck.isBefore(b.getStartTime()) && slotToCheck.isBefore(b.getEndTime())
            );

            if (!isBooked && !isBlocked) {
                availableTimes.add(slotToCheck);
            }

            currentSlot = currentSlot.plusMinutes(slotDurationInMinutes);
        }

        return availableTimes;
    }
}