package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.dto.ScheduleOverride.ScheduleOverrideRequestDTO;
import com.webedu.ben_barber.dto.WorkingHours.WorkingHoursResponseDTO;
import com.webedu.ben_barber.dto.TimeBlock.TimeBlockRequestDTO;
import com.webedu.ben_barber.dto.WorkingHours.WorkingHoursRequestDTO;
import com.webedu.ben_barber.entities.TimeBlock;
import com.webedu.ben_barber.entities.WorkingHours;
import com.webedu.ben_barber.services.ScheduleManagementService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/management/schedule")
public class ScheduleManagementController {

    @Autowired
    private ScheduleManagementService scheduleManagementService;

    @PutMapping("/barbers/{barberId}/working-hours")
    public ResponseEntity<Void> setWorkingHours(@PathVariable Long barberId, @RequestBody WorkingHoursRequestDTO dto) {
        scheduleManagementService.setWorkingHours(barberId, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/blocks")
    public ResponseEntity<TimeBlock> createTimeBlock(@RequestBody TimeBlockRequestDTO dto) {
        TimeBlock newBlock = scheduleManagementService.createTimeBlock(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBlock);
    }

    @DeleteMapping("/blocks/{blockId}")
    public ResponseEntity<Void> deleteTimeBlock(@PathVariable Long blockId) {
        scheduleManagementService.deleteTimeBlock(blockId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/barbers/{barberId}/working-hours")
    @Operation(summary = "Busca todas as regras de horário de trabalho de um barbeiro")
    public ResponseEntity<List<WorkingHoursResponseDTO>> getWorkingHours(@PathVariable Long barberId) {
        List<WorkingHours> workingHoursList = scheduleManagementService.getWorkingHoursForBarber(barberId);

        List<WorkingHoursResponseDTO> dtoList = workingHoursList.stream()
                .map(WorkingHoursResponseDTO::new)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/barbers/{barberId}/working-hours/{dayOfWeek}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove uma regra de horário de trabalho para um dia da semana")
    public void deleteWorkingHoursRule(
            @PathVariable Long barberId,
            @PathVariable DayOfWeek dayOfWeek) {

        scheduleManagementService.deleteWorkingHoursRule(barberId, dayOfWeek);
    }

    @PostMapping("/barbers/{barberId}/overrides")
    @Operation(summary = "Cria ou atualiza um horário de trabalho excepcional para uma data específica")
    public ResponseEntity<Void> createOrUpdateOverride(@PathVariable Long barberId,
                                                       @Valid @RequestBody ScheduleOverrideRequestDTO dto) {
        scheduleManagementService.createOrUpdateOverride(barberId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}