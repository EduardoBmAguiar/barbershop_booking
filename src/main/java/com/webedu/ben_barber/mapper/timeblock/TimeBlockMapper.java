package com.webedu.ben_barber.mapper.timeblock;

import com.webedu.ben_barber.dto.TimeBlock.TimeBlockResponseDTO;
import com.webedu.ben_barber.dto.barber.BarberResponseDTO;
import com.webedu.ben_barber.entities.TimeBlock;
import com.webedu.ben_barber.mapper.barber.BarberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeBlockMapper {

    @Autowired
    private BarberMapper barberMapper;

    public TimeBlockResponseDTO toDTO(TimeBlock entity) {

        BarberResponseDTO barberDTO = entity.getBarber() != null ? barberMapper.toDTO(entity.getBarber()) : null;

        return new TimeBlockResponseDTO(
                entity.getId(),
                barberDTO,
                entity.getBlockDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getReason()
        );
    }
}