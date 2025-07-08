package com.webedu.ben_barber.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(
        @NotBlank String status,
        @NotNull Long idClient,
        @NotNull Long idBarber,
        @NotNull Long idOption,
        @NotNull LocalDate appointmentDate,
        @NotNull LocalTime appointmentTime
) {
}