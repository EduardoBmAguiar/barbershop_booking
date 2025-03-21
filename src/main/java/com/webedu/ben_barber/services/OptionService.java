package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
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

    @Transactional
    public Option addOptions(Option option) {
        log.info("New Option created");
        return optionRepository.save(option);
    }

    @Transactional
    public List<Option> findAllOptions() {
        log.info("Finding all Options in repository");
        return optionRepository.findAll();
    }

    @Transactional
    public Option updatePrice(Long id, Option newPrice) {
        log.info("Finding Option by id");
        Option option = optionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id Not Found"));
        log.info("Option Found");
        log.info("Updating Option price");
        option.setPrice(newPrice.getPrice());
        log.info("New Option price updated");
        return optionRepository.save(option);
    }

    public void deleteOption(Long id) {
        log.info("Finding Option by id");
        Option option = optionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id Not Found"));
        log.info("Option Found");
        log.info("Deleting Option");
        try {
            optionRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            option.setName("Option Removed");
            optionRepository.save(option);
        }
    }
}
