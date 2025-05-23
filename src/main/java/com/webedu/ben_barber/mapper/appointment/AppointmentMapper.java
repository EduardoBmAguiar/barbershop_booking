package com.webedu.ben_barber.mapper.appointment;

import com.webedu.ben_barber.dto.appointment.AppointmentRequestDTO;
import com.webedu.ben_barber.dto.appointment.AppointmentResponseDTO;
import com.webedu.ben_barber.entities.Appointment;
import com.webedu.ben_barber.enums.AppointmentStatus;
import com.webedu.ben_barber.mapper.client.ClientMapper;
import com.webedu.ben_barber.mapper.option.OptionMapper;
import com.webedu.ben_barber.mapper.schedulehours.ScheduleHoursMapper;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setIdDate(dto.idDate());
        appointment.setIdClient(dto.idClient());
        appointment.setIdBarber(dto.idBarber());
        appointment.setIdOption(dto.idOption());

        appointment.setStatus(AppointmentStatus.valueOf(dto.status()));
        return appointment;
    }

    public static AppointmentResponseDTO toDTO(Appointment entity) {
        return new AppointmentResponseDTO(
                entity.getId(),
                ScheduleHoursMapper.toDTO(entity.getScheduleHours()),
                ClientMapper.toDTO(entity.getClient()),
                OptionMapper.toDTO(entity.getOption()),
                entity.getStatus().toString()
        );
    }
}
