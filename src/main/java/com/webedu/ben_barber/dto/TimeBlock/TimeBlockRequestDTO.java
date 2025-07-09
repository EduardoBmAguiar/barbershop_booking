package com.webedu.ben_barber.dto.TimeBlock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeBlockRequestDTO(@NotNull Long barberId,
                                  @NotNull LocalDate date,
                                  @NotNull LocalTime startTime,
                                  @NotNull LocalTime endTime,
                                  @NotBlank String reason
) {
}