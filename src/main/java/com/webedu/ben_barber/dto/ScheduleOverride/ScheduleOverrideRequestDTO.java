package com.webedu.ben_barber.dto.ScheduleOverride;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleOverrideRequestDTO(@NotNull LocalDate overrideDate,
                                         @NotNull LocalTime startTime,
                                         @NotNull LocalTime endTime) {
}