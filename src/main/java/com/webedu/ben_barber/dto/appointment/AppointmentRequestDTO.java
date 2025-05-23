package com.webedu.ben_barber.dto.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentRequestDTO(
        @NotNull Long idDate,
        @NotNull String status,
        @NotNull Long idClient,
        @NotNull Long idBarber,
        @NotNull Long idOption
) {
}