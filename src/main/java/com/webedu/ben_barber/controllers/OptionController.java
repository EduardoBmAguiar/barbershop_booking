package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.services.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/options")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @TrackExecutionTime
    @GetMapping
    public ResponseEntity<List<Option>> findAllOptions() {
        log.info("Finding all Options: initiated");
        List<Option> options = optionService.findAllOptions();
        log.info("Finding all Options: completed");
        return ResponseEntity.ok(options);
    }

    @TrackExecutionTime
    @PostMapping
    public ResponseEntity<Option> addOption(@RequestBody Option option) {
        log.info("Adding Option: initiated");
        option = optionService.addOptions(option);
        log.info("Adding Option: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(option.getId()).toUri();
        return ResponseEntity.created(uri).body(option);
    }

    @TrackExecutionTime
    @PatchMapping(value = "/{id}/price")
    public ResponseEntity<Option> updatePrice(@PathVariable Long id, @RequestBody Option option) {
        log.info("Updating Price: initiated");
        option = optionService.updatePrice(id, option);
        log.info("Updating Price: completed");
        return ResponseEntity.ok(option);
    }

    @TrackExecutionTime
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Option> deleteOptionById(@PathVariable Long id) {
        log.info("Deleting Option by Id: initiated");
        optionService.deleteOption(id);
        log.info("Deleting Option by Id: completed");
        return ResponseEntity.noContent().build();
    }
}
