package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Transactional
    public Option addOptions(Option option) {
        return optionRepository.save(option);
    }

    @Transactional
    public List<Option> findAllOptions()  {
        return optionRepository.findAll();
    }
}
