package com.webedu.ben_barber.dto.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentUpdateDTO(String status,
                                   LocalDate newAppointmentDate,
                                   LocalTime newAppointmentTime) {
}
