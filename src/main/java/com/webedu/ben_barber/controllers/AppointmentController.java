 package com.webedu.ben_barber.controllers;

 import com.webedu.ben_barber.annotation.TrackExecutionTime;
 import com.webedu.ben_barber.entities.Appointment;
 import com.webedu.ben_barber.entities.ScheduleHours;
 import com.webedu.ben_barber.services.AppointmentService;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import jakarta.validation.Valid;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

 import java.net.URI;
 import java.util.List;

@Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz A busca pelos Agendamentos já salvos no banco de dados.", summary = "Realiza a busca dos Agendamentos", method = "GET")
    @ApiResponse(responseCode = "200", description = "Agendamentos Retornados")
    @GetMapping
    public ResponseEntity<List<Appointment>> findAllAgendates() {
        log.info("Finding all Appointments: initiated");
        List<Appointment> list = appointmentService.findAll();
        log.info("Finding all Appointments: completed");
        return ResponseEntity.ok(list);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a busca por ID dos Agendamentos já salvos no banco de dados.", summary = "Realiza a busca dos Agendamentos por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca Realizada."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Appointment> findAgendateById(@PathVariable Long id) {
        log.info("Find Appointment by ID: initiated");
        Appointment appointment = appointmentService.findById(id);
        log.info("Find Appointment by ID: completed");
        return ResponseEntity.ok(appointment);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz o salvamento de um Agendamento no banco de dados.", summary = "Realiza o salvamento do Agendamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Appointment> addAgendate(@Valid @RequestBody Appointment appointment) {
        log.info("Adding Appointment: initiated");
        appointment = appointmentService.addAppointment(appointment);
        log.info("Adding Appointment: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(appointment.getId()).toUri();

        return ResponseEntity.created(uri).body(appointment);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a Atualização de um Agendamento no banco de dados.", summary = "Realiza a atualização de um agendamento", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Agendamento atualizado")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Appointment> updateAgendate(@PathVariable Long id, @RequestBody Appointment appointment) {
        log.info("Updating Appointment: initiated");
        appointment = appointmentService.updateAppointment(id, appointment);
        log.info("Updating Appointment: completed");
        return ResponseEntity.ok(appointment);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz A busca pelos horários que há disponivel no dia escolhido.", summary = "Realiza a busca dos horários disponiveis", method = "GET")
    @ApiResponse(responseCode = "200", description = "Horarios disponiveis retornado")
    @GetMapping(value = "/available-hours")
    public ResponseEntity<List<ScheduleHours>> findAvailableHours(@RequestParam(value = "barberId") Long barberId,
                                                                  @RequestParam(value = "chosenDay") Integer chosenDay
    ) {
        log.info("FindAvailableHours: initiated");
        List<ScheduleHours> list = appointmentService.findHoursAvailableOfDay(barberId, chosenDay);
        log.info("FindAvailableHours: completed");
        return ResponseEntity.ok(list);
    }
 }
