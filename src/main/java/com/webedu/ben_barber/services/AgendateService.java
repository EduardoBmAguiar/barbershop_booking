package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Agendate;
import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.exceptions.InvalidDateException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.AgendateRepository;
import com.webedu.ben_barber.repositories.OptionRepository;
import com.webedu.ben_barber.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendateService {

    @Autowired
    AgendateRepository agendateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OptionRepository optionRepository;

    private final List<LocalDateTime> hoursAvailable;

    public AgendateService() {
        this.hoursAvailable = new ArrayList<>();
        this.generatorHoursAvailable();
    }


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
    public Agendate addAgendate(Agendate agendate, LocalDateTime date) {

        User user = userRepository.findById(agendate.getIdClient()).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Option option = optionRepository.findById(agendate.getIdOption()).orElseThrow(() -> new ResourceNotFoundException("Option Not Found"));

        LocalDateTime chosenDate = hoursAvailable.stream()
                .filter(d -> d.getHour() == date.getHour() && d.getMinute() == date.getMinute() && d.getDayOfMonth() == date.getDayOfMonth() && d.getMonth() == date.getMonth())
                .findFirst().orElseThrow(() -> new InvalidDateException("The chosen date must be a future day"));

        agendate.setClient(user);
        agendate.setChosenDate(chosenDate);
        agendate.setOption(option);

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
    public List<LocalDateTime> findHoursAvailableOfDay(Integer chosenDay) {
        LocalDate today = LocalDate.now();

        LocalDate cD = LocalDate.of(today.getYear(), today.getMonth(), chosenDay);

        List<LocalDateTime> chosenDates = hoursAvailable;
        chosenDates = chosenDates.stream().filter(d -> d.toLocalDate().equals(cD)).collect(Collectors.toList());

        return chosenDates;
    }

    public void generatorHoursAvailable() {
        LocalDate today = LocalDate.now();
        LocalDateTime init = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 9, 0);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(18, 0);

        HoursGenerator(init, start, end);
    }

    public void HoursGenerator(LocalDateTime init, LocalTime start, LocalTime end) {

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
    }
}
