package com.webedu.ben_barber.dto.appointment;

import com.webedu.ben_barber.dto.client.ClientResponseDTO;
import com.webedu.ben_barber.dto.option.OptionResponseDTO;
import com.webedu.ben_barber.dto.schedulehours.ScheduleHoursResponseDTO;

public record AppointmentResponseDTO(
        Long id,
        ScheduleHoursResponseDTO scheduleHours,
        ClientResponseDTO client,
        OptionResponseDTO option, // OptionResponseDTO
        String status
) {
}