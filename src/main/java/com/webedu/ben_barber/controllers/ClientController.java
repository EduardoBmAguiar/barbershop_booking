package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.dto.ClientDTO;
import com.webedu.ben_barber.dto.ClientMapper;
import com.webedu.ben_barber.entities.Client;
import com.webedu.ben_barber.services.ClientService;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz o salvamento de um Cliente no banco de dados.", summary = "Realiza o salvamento de um Cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<ClientDTO> addClient(@Valid @RequestBody ClientDTO dto) {
        log.info("Adding client: initiated");
        Client client = clientService.addClient(ClientMapper.toEntity(dto));
        log.info("Adding client: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(ClientMapper.toDTO(client));
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a Atualização de um Cliente no banco de dados.", summary = "Realiza a atualização de um Cliente", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado")
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO dto, @PathVariable Long id) {
        log.info("Updating client: initiated");
        Client updateClient = clientService.updateClient(id, ClientMapper.toEntity(dto));
        log.info("Updating client: completed");
        return ResponseEntity.ok(ClientMapper.toDTO(updateClient));
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz A busca pelos Clientes já salvos no banco de dados.", summary = "Realiza a busca dos Clientes", method = "GET")
    @ApiResponse(responseCode = "200", description = "Clientes Retornados")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAllClients() {
        log.info("Finding all users: initiated");
        List<Client> clients = clientService.findAllClients();
        List<ClientDTO> dtoList = clients.stream().map(ClientMapper::toDTO).collect(Collectors.toList());
        log.info("Finding all users: completed");
        return ResponseEntity.ok(dtoList);
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a busca por ID dos Clientes já salvos no banco de dados.", summary = "Realiza a busca dos Cliente por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca Realizada."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        log.info("Finding client by id: initiated");
        Client client = clientService.findClientById(id);
        log.info("Finding client by id: completed");
        return ResponseEntity.ok(ClientMapper.toDTO(client));
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a deleção de um Cliente.", summary = "Realiza a deleção de um Cliente", method = "DELETE")
    @ApiResponse(responseCode = "204", description = "Cliente deletado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id) {
        log.info("Deleting user by id: initiated");
        clientService.deleteClient(id);
        log.info("Deleting user by id: completed");
        return ResponseEntity.noContent().build();
    }
}
