package com.webedu.ben_barber.mapper.barber;

import com.webedu.ben_barber.dto.barber.BarberRequestDTO;
import com.webedu.ben_barber.dto.barber.BarberResponseDTO;
import com.webedu.ben_barber.entities.Barber;
import org.springframework.stereotype.Component;

@Component
public class BarberMapper {

    public BarberResponseDTO toDTO(Barber entity) {
        return new BarberResponseDTO(entity.getId(), entity.getName(), entity.getEmail());
    }

    public Barber toEntity(BarberRequestDTO dto) {
        return new Barber(null, dto.name(), dto.email(), dto.password());
    }
}