package com.webedu.ben_barber.dto;

import com.webedu.ben_barber.entities.Client;

public class ClientMapper {

    public static ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getEmail());
    }

    public static Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.id());
        client.setName(dto.name());
        client.setEmail(dto.email());
        return client;
    }
}