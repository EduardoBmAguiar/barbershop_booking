package com.webedu.ben_barber.mapper.barber;

import com.webedu.ben_barber.dto.barber.BarberRequestDTO;
import com.webedu.ben_barber.dto.barber.BarberResponseDTO;
import com.webedu.ben_barber.entities.Barber;

public class BarberMapper {

    public static BarberResponseDTO toDTO(Barber entity) {
        return new BarberResponseDTO(entity.getId(), entity.getName(), entity.getEmail());
    }

    public static Barber toEntity(BarberRequestDTO dto) {
        return new Barber(null, dto.name(), dto.email(), dto.password());
    }
}