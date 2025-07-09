package com.webedu.ben_barber.dto.TimeBlock;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeBlockRequestDTO(Long barberId,
                                  @NotNull LocalDate date,
                                  LocalTime startTime,
                                  LocalTime endTime,
                                  String reason
) {
}