package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.dto.option.OptionRequestDTO;
import com.webedu.ben_barber.dto.option.OptionResponseDTO;
import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.mapper.option.OptionMapper;
import com.webedu.ben_barber.services.OptionService;
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
@RequestMapping(value = "/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz A busca pelas Opções já salvas no banco de dados.", summary = "Realiza a busca das Opções", method = "GET")
    @ApiResponse(responseCode = "200", description = "Opções retornadas")
    @GetMapping
    public ResponseEntity<List<OptionResponseDTO>> findAllOptions() {
        log.info("Finding all Options: initiated");
        List<Option> options = optionService.findAllOptions();
        List<OptionResponseDTO> dtoList = options.stream().map(OptionMapper::toDTO).toList();
        log.info("Finding all Options: completed");
        return ResponseEntity.ok(dtoList);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz o salvamento de uma Opção no banco de dados.", summary = "Realiza o salvamento de uma Opção", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Opção criada"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<OptionResponseDTO> addOption(@Valid @RequestBody OptionRequestDTO dto) {
        log.info("Adding Option: initiated");
        Option option = optionService.addOptions(OptionMapper.toEntity(dto));
        log.info("Adding Option: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(option.getId()).toUri();
        return ResponseEntity.created(uri).body(OptionMapper.toDTO(option));
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a Atualização do preço de uma Opção.", summary = "Realiza a atualização de um preço", method = "PATCH")
    @ApiResponse(responseCode = "200", description = "Preço atualizado")
    @PatchMapping(value = "/{id}/price")
    public ResponseEntity<OptionResponseDTO> updatePrice(@PathVariable Long id, @RequestBody OptionRequestDTO dto) {
        log.info("Updating Price: initiated");
        Option option = optionService.updatePrice(id, OptionMapper.toEntity(dto));
        log.info("Updating Price: completed");
        return ResponseEntity.ok(OptionMapper.toDTO(option));
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a exclusão de uma Opção.", summary = "Realiza a deleção de uma Opção", method = "DELETE")
    @ApiResponse(responseCode = "204", description = "Opção deletada")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOptionById(@PathVariable Long id) {
        log.info("Deleting Option by Id: initiated");
        optionService.deleteOption(id);
        log.info("Deleting Option by Id: completed");
        return ResponseEntity.noContent().build();
    }
}
