package com.webedu.ben_barber.services;

import com.webedu.ben_barber.dto.client.ClientRequestDTO;
import com.webedu.ben_barber.entities.Client;
import com.webedu.ben_barber.exceptions.DatabaseException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.mapper.client.ClientMapper;
import com.webedu.ben_barber.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Transactional
    public Client addClient(ClientRequestDTO client) {
        log.info("New Client created");
        return clientRepository.save(clientMapper.toEntity(client));
    }

    @Transactional
    public Client updateClient(Long id, ClientRequestDTO dto) {
        log.info("Finding client by Id");
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        log.info("Client found");

        if (dto.name() != null && !dto.name().isBlank()) {
            existingClient.setName(dto.name());
            log.info("Updating client name.");
        }

        if (dto.email() != null && !dto.email().isBlank()) {
            existingClient.setEmail(dto.email());
            log.info("Updating client email.");
        }

        if (dto.password() != null && !dto.password().isBlank()) {
            existingClient.setPassword(dto.password()); // Quando se tiver o Spring Security, colocar o Hash
            log.info("Updating client password.");
        }

        log.info("Client updated");
        return clientRepository.save(existingClient);
    }

    @Transactional
    public List<Client> findAllClients() {
        log.info("Finding all clients");
        return clientRepository.findAll();
    }

    @Transactional
    public Client findClientById(Long id) {
        log.info("Finding client by Id");
        Optional<Client> userOptional = clientRepository.findById(id);
        log.info("Client found");
        return userOptional.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void deleteClient(Long id) {
        log.info("Deleting client by Id");
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            clientRepository.delete(client);
        } catch (DataIntegrityViolationException e) {
            log.error("Integrity violation while deleting client {}", id);
            throw new DatabaseException(e.getMessage());
        }
    }
}
