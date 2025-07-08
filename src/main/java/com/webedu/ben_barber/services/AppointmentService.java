package com.webedu.ben_barber.services;

import com.webedu.ben_barber.dto.appointment.AppointmentRequestDTO;
import com.webedu.ben_barber.dto.appointment.AppointmentUpdateDTO;
import com.webedu.ben_barber.entities.*;
import com.webedu.ben_barber.enums.AppointmentStatus;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    WorkingHoursRepository workingHoursRepository;

    @Transactional
    public Appointment findById(Long id) {
        log.info("Finding Appointment by id in repository");
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with " + id + " not found!"));
    }

    @Transactional
    public Appointment addAppointment(AppointmentRequestDTO dto) {
        log.info("Validating if the requested time is within working hours...");

        DayOfWeek dayOfWeek = dto.appointmentDate().getDayOfWeek();

        WorkingHours workingHours = workingHoursRepository
                .findByBarberIdAndDayOfWeek(dto.idBarber(), dayOfWeek)
                .orElseThrow(() -> new IllegalStateException("Agendamento inválido: O barbeiro não trabalha neste dia da semana."));

        LocalTime requestedTime = dto.appointmentTime();
        if (requestedTime.isBefore(workingHours.getStartTime()) || requestedTime.isAfter(workingHours.getEndTime())) {
            throw new IllegalStateException("Agendamento inválido: O horário solicitado está fora do expediente de trabalho.");
        }

        log.info("Verifying if slot is available for Barber ID {} on {} at {}", dto.idBarber(), dto.appointmentDate(), dto.appointmentTime());
        Optional<ScheduleHours> existingSlot = hoursRepository.findByBarberIdAndDateAndHourTime(
                dto.idBarber(),
                dto.appointmentDate(),
                dto.appointmentTime()
        );

        if (existingSlot.isPresent()) {
            throw new IllegalStateException("Conflito: este horário não está mais disponível.");
        }

        log.info("Finding Client by id {}", dto.idClient());
        Client client = findEntityById(dto.idClient(), clientRepository, "Client");

        log.info("Finding Option by id {}", dto.idOption());
        Option option = findEntityById(dto.idOption(), optionRepository, "Option");

        log.info("Finding Barber by id {}", dto.idBarber());
        Barber barber = findEntityById(dto.idBarber(), barberRepository, "Barber");

        log.info("Creating and occupying the scheduled time");
        ScheduleHours bookedSlot = new ScheduleHours(dto.appointmentDate(), dto.appointmentTime());
        bookedSlot.setBarber(barber);
        bookedSlot.setAvailable(false);
        ScheduleHours savedSlot = hoursRepository.save(bookedSlot);

        Appointment newAppointment = new Appointment();

        log.info("Building the new appointment");
        newAppointment.setClient(client);
        newAppointment.setOption(option);
        newAppointment.setBarber(barber);
        newAppointment.setScheduleHours(savedSlot);
        newAppointment.setStatus(AppointmentStatus.SCHEDULED);

        log.info("Saving new appointment");
        return appointmentRepository.save(newAppointment);
    }

    private <T> T findEntityById(Long id, JpaRepository<T, Long> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName + " with id " + id + " not found"));
    }

    @Transactional
    public Appointment updateAppointment(Long appointmentId, AppointmentUpdateDTO dto) {
        log.info("Updating appointment with ID: {}", appointmentId);

        Appointment appointmentToUpdate = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento com ID " + appointmentId + " não encontrado."));

        if (dto.status() != null && !dto.status().isBlank()) {
            try {
                AppointmentStatus newStatus = AppointmentStatus.valueOf(dto.status().toUpperCase());
                log.info("Updating status to {}", newStatus);
                appointmentToUpdate.setStatus(newStatus);

            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status '" + dto.status() + "' é inválido.");
            }
        }

        boolean isRescheduling = dto.newAppointmentDate() != null && dto.newAppointmentTime() != null;
        if (isRescheduling) {
            log.info("Rescheduling appointment to {} at {}", dto.newAppointmentDate(), dto.newAppointmentTime());

            Optional<ScheduleHours> existingSlotInNewTime = hoursRepository.findByBarberIdAndDateAndHourTime(
                    appointmentToUpdate.getBarber().getId(),
                    dto.newAppointmentDate(),
                    dto.newAppointmentTime()
            );

            if (existingSlotInNewTime.isPresent()) {
                throw new IllegalStateException("Conflito: o novo horário escolhido já está ocupado.");
            }

            ScheduleHours oldSlot = appointmentToUpdate.getScheduleHours();
            log.info("Releasing old time slot ID: {}", oldSlot.getId());
            hoursRepository.delete(oldSlot);

            ScheduleHours newSlot = new ScheduleHours(dto.newAppointmentDate(), dto.newAppointmentTime());
            newSlot.setBarber(appointmentToUpdate.getBarber());
            newSlot.setAvailable(false);
            ScheduleHours savedNewSlot = hoursRepository.save(newSlot);
            log.info("Claimed new time slot ID: {}", savedNewSlot.getId());

            appointmentToUpdate.setScheduleHours(savedNewSlot);
        }

        log.info("Saving updated appointment...");
        return appointmentRepository.save(appointmentToUpdate);
    }

    @Transactional
    public List<Appointment> findAppointmentsByBarberAndDay(Long barberId, LocalDate date) {
        log.info("Finding appointments for barber ID {} on date {}", barberId, date);

        if (!barberRepository.existsById(barberId)) {
            throw new EntityNotFoundException("Barbeiro com ID " + barberId + " não encontrado.");
        }

        return appointmentRepository.findByBarberIdAndScheduleHours_DateOrderByScheduleHours_HourTimeAsc(barberId, date);
    }
}