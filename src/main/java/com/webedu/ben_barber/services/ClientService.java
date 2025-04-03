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
    public Client addUser(Client client) {
        log.info("New Client created");
        return clientRepository.save(client);
    }

    @Transactional
    public Client updateUser(Long id, Client client) {
        log.info("Finding client by Id");
        Optional<Client> userOptional = clientRepository.findById(id);
        log.info("Client found");
        log.info("Updating Client");
        if (userOptional.isPresent()) {
            Client clientToUpdate = userOptional.get();

            if (!(client.getName() == null)) { clientToUpdate.setName(client.getName()); }
            if (!(client.getEmail() == null)) { clientToUpdate.setEmail(client.getEmail()); }
            if (!(client.getPassword() == null)) { clientToUpdate.setPassword(client.getPassword()); }

            log.info("Client updated");
            return clientRepository.save(clientToUpdate);
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    @Transactional
    public List<Client> findAllUsers() {
        log.info("Finding all users");
        return clientRepository.findAll();
    }

    @Transactional
    public Client findById(Long id) {
        log.info("Finding user by Id");
        Optional<Client> userOptional = clientRepository.findById(id);
        log.info("Client found");
        return userOptional.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting user by Id");
        clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            clientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error("Integrity violation");
            throw new DatabaseException(e.getMessage());
        }
    }
}
