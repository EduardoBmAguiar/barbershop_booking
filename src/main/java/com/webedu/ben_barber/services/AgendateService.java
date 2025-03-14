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

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendateService {

    @Autowired
    AgendateRepository agendateRepository;

    @Autowired
    UserRepository userRepository;

    private final List<LocalDateTime> hoursAvailable = new ArrayList<>();

    @Transactional
    public List<Agendate> findAll() {
        return agendateRepository.findAll();
    }

    @Transactional
    public Agendate findById(Long id) {
        return agendateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendate with " + id + " not found!"));
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

    @Transactional
    public Agendate updateAgendate(Long id, Agendate agendate) {

        Agendate newAgendate = agendateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (!(agendate.getChosenDate() == null)) { newAgendate.setChosenDate(agendate.getChosenDate()); }
        if (!(agendate.getStatus() == null)) { newAgendate.setStatus(agendate.getStatus()); }

        return agendateRepository.save(newAgendate);
    }

    @Transactional
    public List<LocalDateTime> HoursAvailable(Integer chosenDay) {
        LocalDate today = LocalDate.now();
        LocalDateTime init = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 9, 0);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(18, 0);

        LocalDate cD = LocalDate.of(today.getYear(), today.getMonth(), chosenDay);

        List<LocalDateTime> chosenDates = HoursGenerator(init, start, end);
        chosenDates = chosenDates.stream().filter(d -> d.toLocalDate().equals(cD)).collect(Collectors.toList());

        return chosenDates;
    }

    public List<LocalDateTime> HoursGenerator(LocalDateTime init, LocalTime start, LocalTime end) {

        for (int i = 0; i <= 14; i++) {
            if (init.getDayOfWeek() != DayOfWeek.MONDAY && init.getDayOfWeek() != DayOfWeek.SUNDAY) {
                LocalDateTime current = LocalDateTime.of(init.toLocalDate(), start);

                while (current.toLocalTime().isBefore(end)) {
                    hoursAvailable.add(current);
                    current = current.plusMinutes(30);
                }
            }
            init = init.plusDays(1);
        }
        return hoursAvailable;
    }
}
