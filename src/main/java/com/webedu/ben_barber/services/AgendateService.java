package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Agendate;
import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.exceptions.InvalidDateException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.AgendateRepository;
import com.webedu.ben_barber.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class AgendateService {

    @Autowired
    AgendateRepository agendateRepository;

    @Autowired
    UserRepository userRepository;

    public List<Agendate> findAll() {
        return agendateRepository.findAll();
    }

    @Transactional
    public Agendate addAgendate(Long id, Agendate agendate) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (agendate.getChosenDate().isBefore(Instant.now())) {
            throw new InvalidDateException("Chosen date is after now");
        }

        boolean busyDate = agendateRepository.existsByChosenDate(agendate.getChosenDate());
        if (busyDate) {
            throw new InvalidDateException("already busy date");
        }

        agendate.setClient(user);

        return agendateRepository.save(agendate);



    }


}
