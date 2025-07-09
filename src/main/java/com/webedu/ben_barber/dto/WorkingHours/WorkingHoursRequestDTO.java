package com.webedu.ben_barber.dto.WorkingHours;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record WorkingHoursRequestDTO(@NotNull DayOfWeek dayOfWeek,
                                     @NotNull LocalTime startTime,
                                     @NotNull LocalTime endTime
) {}