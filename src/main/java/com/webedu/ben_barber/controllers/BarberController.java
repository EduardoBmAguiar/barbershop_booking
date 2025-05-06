package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.entities.Barber;
import com.webedu.ben_barber.services.BarberService;
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
@RequestMapping("/barbers")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz o salvamento de um Barbeiro no banco de dados.", summary = "Realiza o salvamento de um Barbeiro", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Barbeiro criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Barber> addBarber(@Valid @RequestBody Barber barber) {
        log.info("Adding barber: initiated");
        barber = barberService.addBarber(barber);
        log.info("Adding barber: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(barber.getId()).toUri();
        return ResponseEntity.created(uri).body(barber);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a Atualização de um Barbeiro no banco de dados.", summary = "Realiza a atualização de um Barbeiro", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Barbeiro atualizado")
    @PutMapping("/{id}")
    public ResponseEntity<Barber> updateBarber(@RequestBody Barber barber, @PathVariable Long id) {
        log.info("Updating barber: initiated");
        Barber updateBarber = barberService.updateBarber(id, barber);
        log.info("Updating barber: completed");
        return ResponseEntity.ok(updateBarber);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz A busca pelos Barbeiro já salvos no banco de dados.", summary = "Realiza a busca dos Barbeiros", method = "GET")
    @ApiResponse(responseCode = "200", description = "Usuários Retornados")
    @GetMapping
    public ResponseEntity<List<Barber>> findAllBarbers() {
        log.info("Finding all barbers: initiated");
        List<Barber> list = barberService.findAllBarbers();
        log.info("Finding all barbers: completed");
        return ResponseEntity.ok(list);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a busca por ID dos Barbeiros já salvos no banco de dados.", summary = "Realiza a busca dos Barbeiros por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca Realizada."),
            @ApiResponse(responseCode = "404", description = "Barbeiro não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Barber> findBarberById(@PathVariable Long id) {
        log.info("Finding barber by id: initiated");
        Barber barber = barberService.findBarberById(id);
        log.info("Finding barber by id: completed");
        return ResponseEntity.ok(barber);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a deleção de um Usuário.", summary = "Realiza a deleção de um Usuário", method = "DELETE")
    @ApiResponse(responseCode = "204", description = "Usuário deletado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarberById(@PathVariable Long id) {
        log.info("Deleting user by id: initiated");
        barberService.deleteBarber(id);
        log.info("Deleting user by id: completed");
        return ResponseEntity.noContent().build();
    }
}
