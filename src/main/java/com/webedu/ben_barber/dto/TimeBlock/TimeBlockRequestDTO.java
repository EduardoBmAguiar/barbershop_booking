package com.webedu.ben_barber.dto.TimeBlock;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeBlockRequestDTO(
        Long barberId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String reason
) {}