package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.entities.Client;
import com.webedu.ben_barber.services.ClientService;
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
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @TrackExecutionTime
    @Operation(description = "Está requizição faz o salvamento de um Usuário no banco de dados.", summary = "Realiza o salvamento de um Usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Client> addUser(@RequestBody Client client) {
        log.info("Adding client: initiated");
        client = clientService.addUser(client);
        log.info("Adding client: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a Atualização de um Usuário no banco de dados.", summary = "Realiza a atualização de um Usuário", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado")
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateUser(@RequestBody Client client, @PathVariable Long id) {
        log.info("Updating client: initiated");
        Client updateClient = clientService.updateUser(id, client);
        log.info("Updating client: completed");
        return ResponseEntity.ok(updateClient);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz A busca pelos Usuários já salvos no banco de dados.", summary = "Realiza a busca dos Usuários", method = "GET")
    @ApiResponse(responseCode = "200", description = "Usuários Retornados")
    @GetMapping
    public ResponseEntity<List<Client>> findAllUsers() {
        log.info("Finding all users: initiated");
        List<Client> list = clientService.findAllUsers();
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
    public ResponseEntity<Client> findUserById(@PathVariable Long id) {
        log.info("Finding client by id: initiated");
        Client client = clientService.findById(id);
        log.info("Finding client by id: completed");
        return ResponseEntity.ok(client);
    }

    @TrackExecutionTime
    @Operation(description = "Está requizição faz a deleção de um Usuário.", summary = "Realiza a deleção de um Usuário", method = "DELETE")
    @ApiResponse(responseCode = "200", description = "Usuário deletado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteUserById(@PathVariable Long id) {
        log.info("Deleting user by id: initiated");
        clientService.delete(id);
        log.info("Deleting user by id: completed");
        return ResponseEntity.noContent().build();
    }
}
