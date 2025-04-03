package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Barber;
import com.webedu.ben_barber.exceptions.DatabaseException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.BarberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BarberService {

    @Autowired
    private BarberRepository barberRepository;

    @Transactional
    public Barber addUser(Barber barber) {
        log.info("New Barber created");
        return barberRepository.save(barber);
    }

    @Transactional
    public Barber updateUser(Long id, Barber barber) {
        log.info("Finding barber by Id");
        Optional<Barber> userOptional = barberRepository.findById(id);
        log.info("Barber found");
        log.info("Updating Barber");
        if (userOptional.isPresent()) {
            Barber barberToUpdate = userOptional.get();

            if (!(barber.getName() == null)) { barberToUpdate.setName(barber.getName()); }
            if (!(barber.getEmail() == null)) { barberToUpdate.setEmail(barber.getEmail()); }
            if (!(barber.getPassword() == null)) { barberToUpdate.setPassword(barber.getPassword()); }

            log.info("Barber updated");
            return barberRepository.save(barberToUpdate);
        } else {
            throw new RuntimeException("Barber not found");
        }
    }

    @Transactional
    public List<Barber> findAllUsers() {
        log.info("Finding all users");
        return barberRepository.findAll();
    }

    @Transactional
    public Barber findById(Long id) {
        log.info("Finding user by Id");
        Optional<Barber> userOptional = barberRepository.findById(id);
        log.info("Barber found");
        return userOptional.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting user by Id");
        barberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            barberRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error("Integrity violation");
            throw new DatabaseException(e.getMessage());
        }
    }
}
