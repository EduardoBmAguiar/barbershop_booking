package com.webedu.ben_barber.mapper.option;

import com.webedu.ben_barber.dto.option.OptionRequestDTO;
import com.webedu.ben_barber.dto.option.OptionResponseDTO;
import com.webedu.ben_barber.entities.Option;
import org.springframework.stereotype.Component;

@Component
public class OptionMapper {

    public OptionResponseDTO toDTO(Option entity) {
        return new OptionResponseDTO(entity.getId(), entity.getName(), entity.getPrice());
    }

    public Option toEntity(OptionRequestDTO dto) {
        return new Option(null, dto.name(), dto.price());
    }
}