package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Agendate;
import com.webedu.ben_barber.entities.Client;
import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.exceptions.InvalidDateException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.AgendateRepository;
import com.webedu.ben_barber.repositories.OptionRepository;
import com.webedu.ben_barber.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AgendateService {

    @Autowired
    AgendateRepository agendateRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    HoursGeneratorService hoursGeneratorService;

    @Transactional
    public List<Agendate> findAll() {
        log.info("Finding all in repository");
        return agendateRepository.findAll();
    }

    @Transactional
    public Agendate findById(Long id) {
        log.info("Finding agendate by id in repository");
        return agendateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendate with " + id + " not found!"));
    }

    @Transactional
    public Agendate addAgendate(Agendate agendate, LocalDateTime date) {
        log.info("Finding Client by id");
        Client client = clientRepository.findById(agendate.getIdClient()).orElseThrow(() -> new ResourceNotFoundException("Client Not Found"));
        log.info("Client found");

        log.info("Finding Option by id");
        Option option = optionRepository.findById(agendate.getIdOption()).orElseThrow(() -> new ResourceNotFoundException("Option Not Found"));
        log.info("Option found");

        LocalDateTime chosenDate = hoursGeneratorService.hoursAvailable.stream()
                .filter(d -> d.getHour() == date.getHour() && d.getMinute() == date.getMinute() && d.getDayOfMonth() == date.getDayOfMonth() && d.getMonth() == date.getMonth())
                .findFirst().orElseThrow(() -> new InvalidDateException("Date is not on our agenda"));

        log.info("Added Client in the agendate");
        agendate.setClient(client);
        log.info("Added chosen Date in the agendate");
        agendate.setChosenDate(chosenDate);
        log.info("Added option in the agendate");
        agendate.setOption(option);

        log.info("Occupying the scheduled time");
        hoursGeneratorService.hoursAvailable.remove(chosenDate);
        log.info("New agendate created");
        return agendateRepository.save(agendate);
    }

    @Transactional
    public Agendate updateAgendate(Long id, Agendate agendate) {
        log.info("Finding if agendate exists");
        Agendate newAgendate = agendateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client Not Found"));

        log.info("checking if the Date has been changed");
        if (!(agendate.getChosenDate() == null)) { newAgendate.setChosenDate(agendate.getChosenDate()); }
        log.info("checking if the Status has been changed");
        if (!(agendate.getStatus() == null)) { newAgendate.setStatus(agendate.getStatus()); }

        log.info("Updating agendate");
        return agendateRepository.save(newAgendate);
    }

    @Transactional
    public List<LocalDateTime> findHoursAvailableOfDay(Integer chosenDay) {
        LocalDate today = LocalDate.now();

        LocalDate cD = LocalDate.of(today.getYear(), today.getMonth(), chosenDay);

        log.info("Finding hours available of chosen day");
        List<LocalDateTime> chosenDates = hoursGeneratorService.hoursAvailable;
        chosenDates = chosenDates.stream().filter(d -> d.toLocalDate().equals(cD)).collect(Collectors.toList());

        return chosenDates;
    }
}
