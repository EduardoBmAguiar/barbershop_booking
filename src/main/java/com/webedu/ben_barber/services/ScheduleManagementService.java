package com.webedu.ben_barber.services;

import com.webedu.ben_barber.dto.ScheduleOverride.ScheduleOverrideRequestDTO;
import com.webedu.ben_barber.dto.TimeBlock.TimeBlockRequestDTO;
import com.webedu.ben_barber.dto.WorkingHours.WorkingHoursRequestDTO;
import com.webedu.ben_barber.entities.*;
import com.webedu.ben_barber.enums.AppointmentStatus;
import com.webedu.ben_barber.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ScheduleManagementService {

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private TimeBlockRepository timeBlockRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ScheduleOverrideRepository overrideRepository;

    @Transactional
    public void setWorkingHours(Long barberId, WorkingHoursRequestDTO dto) {

        if (dto.startTime().isAfter(dto.endTime())) {
            throw new IllegalArgumentException("O horário de início não pode ser depois do horário de término.");
        }

        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new EntityNotFoundException("Barbeiro com ID " + barberId + " não encontrado."));

        WorkingHours workingHours = workingHoursRepository
                .findByBarberIdAndDayOfWeek(barberId, dto.dayOfWeek())
                .orElse(new WorkingHours());

        workingHours.setBarber(barber);
        workingHours.setDayOfWeek(dto.dayOfWeek());
        workingHours.setStartTime(dto.startTime());
        workingHours.setEndTime(dto.endTime());

        workingHoursRepository.save(workingHours);
    }

    @Transactional
    public TimeBlock createTimeBlock(TimeBlockRequestDTO dto) {
        TimeBlock block = new TimeBlock();

        if (dto.barberId() != null) {
            Barber barber = barberRepository.findById(dto.barberId())
                    .orElseThrow(() -> new EntityNotFoundException("Barbeiro com ID " + dto.barberId() + " não encontrado."));
            block.setBarber(barber);
        }

        block.setBlockDate(dto.date());
        block.setStartTime(dto.startTime());
        block.setEndTime(dto.endTime());
        block.setReason(dto.reason());

        return timeBlockRepository.save(block);
    }

    @Transactional
    public void deleteTimeBlock(Long blockId) {
        if (!timeBlockRepository.existsById(blockId)) {
            throw new EntityNotFoundException("Bloqueio de tempo com ID " + blockId + " não encontrado.");
        }
        timeBlockRepository.deleteById(blockId);
    }

    public List<WorkingHours> getWorkingHoursForBarber(Long barberId) {
        log.info("Fetching working hours for barber ID: {}", barberId);

        if (!barberRepository.existsById(barberId)) {
            throw new EntityNotFoundException("Barbeiro com ID " + barberId + " não encontrado.");
        }

        return workingHoursRepository.findByBarberIdOrderByDayOfWeekAsc(barberId);
    }

    @Transactional
    public void deleteWorkingHoursRule(Long barberId, DayOfWeek dayOfWeek) {
        log.info("Attempting to delete working hours for barber {} on {}", barberId, dayOfWeek);

        List<Appointment> futureAppointments = appointmentRepository
                .findByBarberIdAndScheduleHours_DateAfterAndStatusNot(
                        barberId, LocalDate.now().minusDays(1), AppointmentStatus.CANCELED
                );

        boolean hasConflict = futureAppointments.stream()
                .anyMatch(appointment -> appointment.getScheduleHours().getDate().getDayOfWeek() == dayOfWeek);

        if (hasConflict) {
            throw new IllegalStateException(
                    "Não é possível remover este dia de trabalho, pois existem agendamentos futuros. " +
                            "Por favor, cancele ou reagende os agendamentos primeiro."
            );
        }

        WorkingHours ruleToDelete = workingHoursRepository.findByBarberIdAndDayOfWeek(barberId, dayOfWeek)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma regra de trabalho encontrada para este barbeiro em " + dayOfWeek));

        workingHoursRepository.delete(ruleToDelete);
        log.info("Working hours for barber {} on {} deleted successfully.", barberId, dayOfWeek);
    }

    @Transactional
    public void createOrUpdateOverride(Long barberId, ScheduleOverrideRequestDTO dto) {
        if (dto.startTime().isAfter(dto.endTime())) {
            throw new IllegalArgumentException("O horário de início não pode ser depois do horário de término.");
        }

        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new EntityNotFoundException("Barbeiro não encontrado."));

        ScheduleOverride override = overrideRepository.findByBarberIdAndOverrideDate(barberId, dto.overrideDate())
                .orElse(new ScheduleOverride());

        override.setBarber(barber);
        override.setOverrideDate(dto.overrideDate());
        override.setStartTime(dto.startTime());
        override.setEndTime(dto.endTime());

        overrideRepository.save(override);
    }
}