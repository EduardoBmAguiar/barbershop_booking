package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.*;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BarberRepository barberRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    HoursRepository hoursRepository;

    @Transactional
    public List<Appointment> findAll() {
        log.info("Finding all in repository");
        return appointmentRepository.findAll();
    }

    @Transactional
    public Appointment findById(Long id) {
        log.info("Finding Appointment by id in repository");
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with " + id + " not found!"));
    }

    @Transactional
    public Appointment addAppointment(Appointment appointment) {
        log.info("Finding Client by id");
        Client client = findEntityById(appointment.getIdClient(), clientRepository, "Client");
        log.info("Client found");

        log.info("Finding Option by id");
        Option option = findEntityById(appointment.getIdOption(), optionRepository, "Option");
        log.info("Option found");

        log.info("Finding Barber by id");
        Barber barber = findEntityById(appointment.getIdBarber(), barberRepository, "Barber");
        log.info("Barber found");

        log.info("Finding Date by id");
        ScheduleHours scheduleHours = findEntityById(appointment.getIdDate(), hoursRepository, "ScheduleHours");
        log.info("Date found");

        log.info("Added Client in the appointment");
        appointment.setClient(client);
        log.info("Added chosen Date in the appointment");
        appointment.setScheduleHours(scheduleHours);
        log.info("Added option in the appointment");
        appointment.setOption(option);
        log.info("Added Barber in the appointment");
        appointment.setBarber(barber);

        log.info("Occupying the scheduled time");
        hoursRepository.findById(scheduleHours.getId()).get().setAvailable(false);
        log.info("New appointment created");
        return appointmentRepository.save(appointment);
    }

    private <T> T findEntityById(Long id, JpaRepository<T, Long> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName + " with id " + id + " not found"));
    }

    @Transactional
    public Appointment updateAppointment(Long id, Appointment appointment) {
        log.info("Finding if appointment exists");
        Appointment newAppointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client Not Found"));

        log.info("checking if the Date has been changed");
        if (!(appointment.getScheduleHours() == null)) { newAppointment.setScheduleHours(appointment.getScheduleHours()); }
        log.info("checking if the Status has been changed");
        if (!(appointment.getStatus() == null)) { newAppointment.setStatus(appointment.getStatus()); }

        log.info("Updating appointment");
        return appointmentRepository.save(newAppointment);
    }

    @Transactional
    public List<ScheduleHours> findHoursAvailableOfDay(Long barberId, Integer chosenDay) {
        LocalDate today = LocalDate.now();
        LocalDate chosenDate = LocalDate.of(today.getYear(), today.getMonth(), chosenDay);

        log.info("Finding hours available for barber {} on {}", barberId, chosenDate);
        return hoursRepository.findByBarberIdAndDateAndAvailableTrue(barberId, chosenDate);
    }
}
