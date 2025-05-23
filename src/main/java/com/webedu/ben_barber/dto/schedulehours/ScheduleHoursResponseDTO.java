package com.webedu.ben_barber.dto.schedulehours;

import com.webedu.ben_barber.dto.barber.BarberResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleHoursResponseDTO(
        Long id,
        LocalDate date,
        LocalTime hourTime,
        BarberResponseDTO barber
) {
}