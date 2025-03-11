package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/options")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @GetMapping
    public ResponseEntity<List<Option>> findAllOptions() {
        List<Option> options = optionService.findAllOptions();
        return ResponseEntity.ok(options);
    }

    @PostMapping
    public ResponseEntity<Option> addOption(@RequestBody Option option) {
        option = optionService.addOptions(option);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(option.getId()).toUri();
        return ResponseEntity.created(uri).body(option);
    }
}
