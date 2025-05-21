package com.webedu.ben_barber.dto.barber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BarberRequestDTO(@NotBlank String name,
                               @NotNull @Email String email,
                               @NotBlank String password
) {
}