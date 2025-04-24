package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.*;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    BarberRepository barberRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    HoursRepository hoursRepository;

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
    public Agendate addAgendate(Agendate agendate) {
        log.info("Finding Client by id");
        Client client = clientRepository.findById(agendate.getIdClient()).orElseThrow(() -> new ResourceNotFoundException("Client Not Found"));
        log.info("Client found");

        log.info("Finding Option by id");
        Option option = optionRepository.findById(agendate.getIdOption()).orElseThrow(() -> new ResourceNotFoundException("Option Not Found"));
        log.info("Option found");

        log.info("Finding Barber by id");
        Barber barber = barberRepository.findById(agendate.getIdBarber()).orElseThrow(() -> new ResourceNotFoundException("Barber Not Found"));
        log.info("Barber found");

        log.info("Finding Date by id");
        ScheduleHours scheduleHours = hoursRepository.findById(agendate.getIdDate()).orElseThrow(() -> new ResourceNotFoundException("Date Not Found"));
        log.info("Date found");

//        ScheduleHours chosenDate = hoursRepository.findByDateAndHourTime(
//                agendate.getScheduleHours().getDate(),
//                agendate.getScheduleHours().getHourTime())
//                .orElseThrow(() -> new InvalidDateException("Date is not on our agenda"));

        log.info("Added Client in the agendate");
        agendate.setClient(client);
        log.info("Added chosen Date in the agendate");
        agendate.setScheduleHours(scheduleHours);
        log.info("Added option in the agendate");
        agendate.setOption(option);
        log.info("Added Barber in the agendate");
        agendate.setBarber(barber);

        log.info("Occupying the scheduled time");
        hoursRepository.findById(scheduleHours.getId()).get().setAvailable(false);
        log.info("New agendate created");
        return agendateRepository.save(agendate);
    }

    @Transactional
    public Agendate updateAgendate(Long id, Agendate agendate) {
        log.info("Finding if agendate exists");
        Agendate newAgendate = agendateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client Not Found"));

        log.info("checking if the Date has been changed");
        if (!(agendate.getScheduleHours() == null)) { newAgendate.setScheduleHours(agendate.getScheduleHours()); }
        log.info("checking if the Status has been changed");
        if (!(agendate.getStatus() == null)) { newAgendate.setStatus(agendate.getStatus()); }

        log.info("Updating agendate");
        return agendateRepository.save(newAgendate);
    }

    @Transactional
    public List<ScheduleHours> findHoursAvailableOfDay(Long barberId, Integer chosenDay) {
        LocalDate today = LocalDate.now();
        LocalDate chosenDate = LocalDate.of(today.getYear(), today.getMonth(), chosenDay);

        log.info("Finding hours available for barber {} on {}", barberId, chosenDate);
        List<ScheduleHours> hours = hoursRepository.findByBarberIdAndDate(barberId, chosenDate);

        return hours.stream()
                .filter(ScheduleHours::getAvailable)
                .collect(Collectors.toList());
    }
}
