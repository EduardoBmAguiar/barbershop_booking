package com.webedu.ben_barber.dto.WorkingHours;

import com.webedu.ben_barber.entities.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record WorkingHoursResponseDTO(DayOfWeek dayOfWeek,
                                      LocalTime startTime,
                                      LocalTime endTime) {

    public WorkingHoursResponseDTO(WorkingHours entity) {
        this(entity.getDayOfWeek(), entity.getStartTime(), entity.getEndTime());
    }
}