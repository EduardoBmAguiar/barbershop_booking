package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.dto.client.ClientRequestDTO;
import com.webedu.ben_barber.dto.client.ClientResponseDTO;
import com.webedu.ben_barber.entities.Client;
import com.webedu.ben_barber.mapper.client.ClientMapper;
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

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz o salvamento de um Cliente no banco de dados.", summary = "Realiza o salvamento de um Cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<ClientResponseDTO> addClient(@Valid @RequestBody ClientRequestDTO dto) {
        log.info("Adding client: initiated");
        Client client = clientService.addClient(dto);
        log.info("Adding client: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(clientMapper.toDTO(client));
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz a Atualização de um Cliente no banco de dados.", summary = "Realiza a atualização de um Cliente", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado")
    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@RequestBody ClientRequestDTO dto, @PathVariable Long id) {
        log.info("Updating client: initiated");
        Client updateClient = clientService.updateClient(id, dto);
        log.info("Updating client: completed");
        return ResponseEntity.ok(clientMapper.toDTO(updateClient));
    }

    @TrackExecutionTime
    @Operation(description = "Esta requisição faz A busca pelos Clientes já salvos no banco de dados.", summary = "Realiza a busca dos Clientes", method = "GET")
    @ApiResponse(responseCode = "200", description = "Clientes Retornados")
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAllClients() {
        log.info("Finding all users: initiated");
        List<Client> clients = clientService.findAllClients();
        List<ClientResponseDTO> dtoList = clients.stream().map(clientMapper::toDTO).toList();
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
    public ResponseEntity<ClientResponseDTO> findClientById(@PathVariable Long id) {
        log.info("Finding client by id: initiated");
        Client client = clientService.findClientById(id);
        log.info("Finding client by id: completed");
        return ResponseEntity.ok(clientMapper.toDTO(client));
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
