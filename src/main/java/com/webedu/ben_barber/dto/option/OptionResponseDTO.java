package com.webedu.ben_barber.dto.option;

import java.math.BigDecimal;

public record OptionResponseDTO(Long id,
                                String name,
                                BigDecimal price
) {
}