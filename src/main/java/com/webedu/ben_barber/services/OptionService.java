package com.webedu.ben_barber.services;

import com.webedu.ben_barber.dto.option.OptionRequestDTO;
import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.mapper.option.OptionMapper;
import com.webedu.ben_barber.repositories.OptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionMapper optionMapper;

    @Transactional
    public Option addOptions(OptionRequestDTO dto) {
        Option entity = optionMapper.toEntity(dto);
        log.info("New Option created");
        return optionRepository.save(entity);
    }

    public List<Option> findAllOptions() {
        log.info("Finding all Options in repository");
        return optionRepository.findAll();
    }

    @Transactional
    public Option updatePrice(Long id, OptionRequestDTO dto) {
        log.info("Updating price for Option with id: {}", id);
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option with id " + id + " not found"));

        Option newPrice = optionMapper.toEntity(dto);

        option.setPrice(newPrice.getPrice());
        log.info("Price updated to: {}", newPrice.getPrice());
        return optionRepository.save(option);
    }

    public void deleteOption(Long id) {
        log.info("Attempting to delete Option with id: {}", id);
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option with id " + id + " not found"));

        try {
            optionRepository.deleteById(id);
            log.info("Option with id {} deleted successfully", id);
        } catch (DataIntegrityViolationException e) {
            log.warn("Could not delete Option with id {} due to data integrity violation: {}", id, e.getMessage());
        }
    }
}
