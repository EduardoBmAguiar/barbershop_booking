package com.webedu.ben_barber.mapper.schedulehours;

import com.webedu.ben_barber.dto.schedulehours.ScheduleHoursResponseDTO;
import com.webedu.ben_barber.entities.ScheduleHours;
import com.webedu.ben_barber.mapper.barber.BarberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleHoursMapper {
    // Se BarberMapper também for um @Component, você pode injetá-lo
    @Autowired
    private BarberMapper barberMapper;

    public ScheduleHoursResponseDTO toDTO(ScheduleHours entity) {
        return new ScheduleHoursResponseDTO(
                entity.getId(),
                entity.getDate(),
                entity.getHourTime(),
                barberMapper.toDTO(entity.getBarber())
        );
    }
}