package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.entities.Barber;
import com.webedu.ben_barber.services.BarberService;
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
@RequestMapping("/barbers")
public class BarberController {

    @Autowired
    private BarberService barberService;

    @TrackExecutionTime
    @Operation(description = "Está requizição faz o salvamento de um Usuário no banco de dados.", summary = "Realiza o salvamento de um Usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Barber> addUser(@RequestBody Barber barber) {
        log.info("Adding barber: initiated");
        barber = barberService.addUser(barber);
        log.info("Adding barber: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(barber.getId()).toUri();
        return ResponseEntity.created(uri).body(barber);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a Atualização de um Usuário no banco de dados.", summary = "Realiza a atualização de um Usuário", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado")
    @PutMapping("/{id}")
    public ResponseEntity<Barber> updateUser(@RequestBody Barber barber, @PathVariable Long id) {
        log.info("Updating barber: initiated");
        Barber updateBarber = barberService.updateUser(id, barber);
        log.info("Updating barber: completed");
        return ResponseEntity.ok(updateBarber);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz A busca pelos Usuários já salvos no banco de dados.", summary = "Realiza a busca dos Usuários", method = "GET")
    @ApiResponse(responseCode = "200", description = "Usuários Retornados")
    @GetMapping
    public ResponseEntity<List<Barber>> findAllUsers() {
        log.info("Finding all users: initiated");
        List<Barber> list = barberService.findAllUsers();
        log.info("Finding all users: completed");
        return ResponseEntity.ok(list);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a busca por ID dos Usuários já salvos no banco de dados.", summary = "Realiza a busca dos Usuários por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca Realizada."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Barber> findUserById(@PathVariable Long id) {
        log.info("Finding barber by id: initiated");
        Barber barber = barberService.findById(id);
        log.info("Finding barber by id: completed");
        return ResponseEntity.ok(barber);
    }

    @TrackExecutionTime
    @Operation(description = "Está requizição faz a deleção de um Usuário.", summary = "Realiza a deleção de um Usuário", method = "DELETE")
    @ApiResponse(responseCode = "200", description = "Usuário deletado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Barber> deleteUserById(@PathVariable Long id) {
        log.info("Deleting user by id: initiated");
        barberService.delete(id);
        log.info("Deleting user by id: completed");
        return ResponseEntity.noContent().build();
    }
}
