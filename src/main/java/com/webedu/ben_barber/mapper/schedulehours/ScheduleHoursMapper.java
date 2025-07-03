package com.webedu.ben_barber.mapper.schedulehours;

import com.webedu.ben_barber.dto.schedulehours.ScheduleHoursResponseDTO;
import com.webedu.ben_barber.entities.ScheduleHours;
import com.webedu.ben_barber.mapper.barber.BarberMapper;

public class ScheduleHoursMapper {

    public static ScheduleHoursResponseDTO toDTO(ScheduleHours entity) {
        return new ScheduleHoursResponseDTO(entity.getId(),
                entity.getDate(),
                entity.getHourTime(),
                BarberMapper.toDTO(entity.getBarber()));
    }
}