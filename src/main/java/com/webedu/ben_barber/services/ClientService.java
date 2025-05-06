package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Client;
import com.webedu.ben_barber.exceptions.DatabaseException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
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

    @Transactional
    public Client addClient(Client client) {
        log.info("New Client created");
        return clientRepository.save(client);
    }

    @Transactional
    public Client updateClient(Long id, Client client) {
        log.info("Finding client by Id");
        Client clientToUpdate = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        log.info("Updating Client");
        updateFields(client, clientToUpdate);
        log.info("Client updated");

        return clientRepository.save(clientToUpdate);
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

    private void updateFields(Client source, Client target) {
        if (source.getName() != null) { target.setName(source.getName()); }
        if (source.getEmail() != null) { target.setEmail(source.getEmail()); }
        if (source.getPassword() != null) { target.setPassword(source.getPassword()); }
    }
}
