package com.webedu.ben_barber.dto.TimeBlock;

import com.webedu.ben_barber.dto.barber.BarberResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeBlockResponseDTO(Long id,
                                   BarberResponseDTO barber,
                                   LocalDate blockDate,
                                   LocalTime startTime,
                                   LocalTime endTime,
                                   String reason
) {
}