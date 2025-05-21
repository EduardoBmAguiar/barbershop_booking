package com.webedu.ben_barber.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequestDTO(@NotBlank String name,
                               @NotNull @Email String email,
                               @NotBlank String password
) {
}