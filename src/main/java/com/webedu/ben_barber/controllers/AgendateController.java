 package com.webedu.ben_barber.controllers;

 import com.webedu.ben_barber.annotation.TrackExecutionTime;
 import com.webedu.ben_barber.entities.Agendate;
 import com.webedu.ben_barber.entities.ScheduleHours;
 import com.webedu.ben_barber.services.AgendateService;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

 import java.net.URI;
 import java.util.List;

@Slf4j
@RestController
@RequestMapping("/agendates")
public class AgendateController {

    @Autowired
    AgendateService agendateService;

    @TrackExecutionTime
    @Operation(description = "Está requisição faz A busca pelos Agendamentos já salvos no banco de dados.", summary = "Realiza a busca dos Agendamentos", method = "GET")
    @ApiResponse(responseCode = "200", description = "Agendamentos Retornados")
    @GetMapping
    public ResponseEntity<List<Agendate>> findAllAgendates() {
        log.info("Finding all Agendates: initiated");
        List<Agendate> list = agendateService.findAll();
        log.info("Finding all Agendates: completed");
        return ResponseEntity.ok(list);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a busca por ID dos Agendamentos já salvos no banco de dados.", summary = "Realiza a busca dos Agendamentos por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca Realizada."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Agendate> findAgendateById(@PathVariable Long id) {
        log.info("finding Agendate by Id: initiated");
        Agendate agendate = agendateService.findById(id);
        log.info("Finding Agendate by Id: completed");
        return ResponseEntity.ok(agendate);
    }

    @TrackExecutionTime
    @Operation(description = "Está requizição faz o salvamento de um Agendamento no banco de dados.", summary = "Realiza o salvamento do Agendamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Agendate> addAgendate(@RequestBody Agendate agendate) {
        log.info("Adding Agendate: initiated");
        agendate = agendateService.addAgendate(agendate);
        log.info("Adding Agendate: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(agendate.getId()).toUri();

        return ResponseEntity.created(uri).body(agendate);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a Atualização de um Agendamento no banco de dados.", summary = "Realiza a atualização de um agendamento", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Agendamento atualizado")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Agendate> updateAgendate(@PathVariable Long id, @RequestBody Agendate agendate) {
        log.info("updating Agendate: initiated");
        agendate = agendateService.updateAgendate(id, agendate);
        log.info("updating Agendate: completed");
        return ResponseEntity.ok(agendate);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz A busca pelos horários que há disponivel no dia escolhido.", summary = "Realiza a busca dos horários disponiveis", method = "GET")
    @ApiResponse(responseCode = "200", description = "Horarios disponiveis retornado")
    @GetMapping(value = "/hoursForAgendate")
    public ResponseEntity<List<ScheduleHours>> findAvailableHours(@RequestParam(value = "chosenDay") Integer chosenDay) {
        log.info("findAvailableHours: initiated");
        List<ScheduleHours> list = agendateService.findHoursAvailableOfDay(chosenDay);
        log.info("findAvailableHours: completed");
        return ResponseEntity.ok(list);
    }
 }
