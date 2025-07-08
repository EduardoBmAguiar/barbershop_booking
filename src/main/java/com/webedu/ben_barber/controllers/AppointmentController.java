 package com.webedu.ben_barber.controllers;

 import com.webedu.ben_barber.annotation.TrackExecutionTime;
 import com.webedu.ben_barber.dto.appointment.AppointmentRequestDTO;
 import com.webedu.ben_barber.dto.appointment.AppointmentResponseDTO;
 import com.webedu.ben_barber.dto.appointment.AppointmentUpdateDTO;
 import com.webedu.ben_barber.entities.Appointment;
 import com.webedu.ben_barber.mapper.appointment.AppointmentMapper;
 import com.webedu.ben_barber.services.AppointmentService;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import jakarta.validation.Valid;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.format.annotation.DateTimeFormat;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

 import java.net.URI;
 import java.time.LocalDate;
 import java.util.List;

 @Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    public AppointmentController(AppointmentService appointmentService, AppointmentMapper appointmentMapper) {
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a busca dos Agendamentos de um barbeiro específico em um dia específico.", summary = "Realiza a busca de Agendamentos por Barbeiro e Dia", method = "GET")
    @ApiResponse(responseCode = "200", description = "Agendamentos Retornados")
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> findAppointmentsByDay(
            @RequestParam Long barberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
         log.info("Finding appointments request received for barber: {} on date: {}", barberId, date);
         List<Appointment> appointments = appointmentService.findAppointmentsByBarberAndDay(barberId, date);

         List<AppointmentResponseDTO> dtoList = appointments.stream()
                 .map(appointmentMapper::toDTO)
                 .toList();

         log.info("Found {} appointments.", dtoList.size());
         return ResponseEntity.ok(dtoList);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a busca por ID dos Agendamentos já salvos no banco de dados.", summary = "Realiza a busca dos Agendamentos por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca Realizada."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponseDTO> findAppointmentById(@PathVariable Long id) {
        log.info("Find Appointment by ID: initiated");
        Appointment appointment = appointmentService.findById(id);
        log.info("Find Appointment by ID: completed");
        return ResponseEntity.ok(appointmentMapper.toDTO(appointment));
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz o salvamento de um Agendamento no banco de dados.", summary = "Realiza o salvamento do Agendamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Conflito, horário já agendado")
    })
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> addAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
        log.info("Adding Appointment: initiated for client {}", dto.idClient());
        Appointment createdAppointment = appointmentService.addAppointment(dto);
        log.info("Adding Appointment: completed with ID {}", createdAppointment.getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdAppointment.getId()).toUri();

        return ResponseEntity.created(uri).body(appointmentMapper.toDTO(createdAppointment));
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a Atualização de um Agendamento no banco de dados.", summary = "Realiza a atualização de um agendamento", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Agendamento atualizado")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentUpdateDTO dto) {
        log.info("Updating appointment: initiated for ID {}", id);
        Appointment updatedAppointment = appointmentService.updateAppointment(id, dto);
        log.info("Updating appointment: completed for ID {}", id);
        return ResponseEntity.ok(appointmentMapper.toDTO(updatedAppointment));
    }
 }