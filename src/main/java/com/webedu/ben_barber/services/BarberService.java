package com.webedu.ben_barber.services;

import com.webedu.ben_barber.dto.barber.BarberRequestDTO;
import com.webedu.ben_barber.entities.Barber;
import com.webedu.ben_barber.exceptions.DatabaseException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.mapper.barber.BarberMapper;
import com.webedu.ben_barber.repositories.BarberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BarberService {

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private BarberMapper barberMapper;

    @Transactional
    public Barber addBarber(BarberRequestDTO dto) {
        log.info("New Barber created");
        Barber entity = barberMapper.toEntity(dto);
        return barberRepository.save(entity);
    }

    @Transactional
    public Barber updateBarber(Long id, BarberRequestDTO dto) {
        log.info("Finding barber by Id");
        Barber existingBarber = barberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Barber with id " + id + " not found"));
        log.info("Barber found");

        log.info("Updating Barber");
        existingBarber.setName(dto.name());
        existingBarber.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            existingBarber.setPassword(dto.password()); // Lembre-se de aplicar o HASH aqui!
        }

        log.info("Barber updated");
            return barberRepository.save(existingBarber);
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