package com.webedu.ben_barber.dto.option;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OptionRequestDTO(@NotBlank String name,
                               @NotNull BigDecimal price
) {
}