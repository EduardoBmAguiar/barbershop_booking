package com.webedu.ben_barber.mapper.appointment;

import com.webedu.ben_barber.dto.appointment.AppointmentResponseDTO;
import com.webedu.ben_barber.entities.Appointment;
import com.webedu.ben_barber.mapper.client.ClientMapper;
import com.webedu.ben_barber.mapper.option.OptionMapper;
import com.webedu.ben_barber.mapper.schedulehours.ScheduleHoursMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    @Autowired
    private ScheduleHoursMapper scheduleHoursMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private OptionMapper optionMapper;

    public AppointmentResponseDTO toDTO(Appointment entity) {
        return new AppointmentResponseDTO(
                entity.getId(),
                scheduleHoursMapper.toDTO(entity.getScheduleHours()),
                clientMapper.toDTO(entity.getClient()),
                optionMapper.toDTO(entity.getOption()),
                entity.getStatus().toString()
        );
    }
}