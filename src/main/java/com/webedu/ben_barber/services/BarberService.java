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
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class BarberService {

    @Autowired
    private BarberRepository barberRepository;

    @Transactional
    public Barber addBarber(Barber barber) {
        log.info("New Barber created");
        return barberRepository.save(barber);
    }

    @Transactional
    public Barber updateBarber(Long id, Barber barber) {
        log.info("Finding barber by Id");
        Barber barberToUpdate = findBarberOrThrow(id);
        log.info("Barber found");

        log.info("Updating Barber");
        if (Objects.nonNull(barber.getName())) { barberToUpdate.setName(barber.getName()); }
        if (Objects.nonNull(barber.getEmail())) { barberToUpdate.setEmail(barber.getEmail()); }
        if (Objects.nonNull(barber.getPassword())) { barberToUpdate.setPassword(barber.getPassword()); }

        log.info("Barber updated");
            return barberRepository.save(barberToUpdate);
    }

    @Transactional
    public List<Barber> findAllBarbers() {
        log.info("Finding all barbers");
        return barberRepository.findAll();
    }

    @Transactional
    public Barber findBarberById(Long id) {
        log.info("Finding barber by Id");
        Barber barber = findBarberOrThrow(id);
        log.info("Barber found");
        return barber;
    }

    @Transactional
    public void deleteBarber(Long id) {
        log.info("Deleting barber by Id");
        findBarberOrThrow(id);
        try {
            barberRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error("Integrity violation");
            throw new DatabaseException(e.getMessage());
        }
    }

    private Barber findBarberOrThrow(Long id) {
        return barberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
