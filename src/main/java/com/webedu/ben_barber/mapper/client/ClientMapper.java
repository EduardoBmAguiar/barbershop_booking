package com.webedu.ben_barber.mapper.client;

import com.webedu.ben_barber.dto.client.ClientRequestDTO;
import com.webedu.ben_barber.dto.client.ClientResponseDTO;
import com.webedu.ben_barber.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientResponseDTO toDTO(Client entity) {
        return new ClientResponseDTO(entity.getId(), entity.getName(), entity.getEmail());
    }

    public Client toEntity(ClientRequestDTO dto) {
        return new Client(null, dto.name(), dto.email(), dto.password());
    }
}