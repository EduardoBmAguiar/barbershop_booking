package com.webedu.ben_barber.dto.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record WorkingHoursDTO(
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {}