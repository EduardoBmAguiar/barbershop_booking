 package com.webedu.ben_barber.controllers;

 import com.webedu.ben_barber.entities.Agendate;
 import com.webedu.ben_barber.services.AgendateService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

 import java.net.URI;
 import java.time.LocalDateTime;
 import java.util.List;

 @Controller
@RequestMapping("/agendates")
public class AgendateController {

    @Autowired
    AgendateService agendateService;

    @GetMapping
    public ResponseEntity<List<Agendate>> findAllAgendates() {
        List<Agendate> list = agendateService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Agendate> findAgendateById(@PathVariable Long id) {
        Agendate agendate = agendateService.findById(id);

        return ResponseEntity.ok(agendate);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Agendate> addAgendate(@PathVariable Long id, @RequestBody Agendate agendate) {
        agendate = agendateService.addAgendate(id, agendate);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(agendate.getId()).toUri();

        return ResponseEntity.created(uri).body(agendate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Agendate> updateAgendate(@PathVariable Long id, @RequestBody Agendate agendate) {
        agendate = agendateService.updateAgendate(id, agendate);
        return ResponseEntity.ok(agendate);
    }

    @GetMapping(value = "/hoursForAgendate")
    public ResponseEntity<List<LocalDateTime>> findAvailableHours(@RequestParam(value = "chosenDay", defaultValue = "null") Integer chosenDay) {
        List<LocalDateTime> list = agendateService.HoursAvailable(chosenDay);
        return ResponseEntity.ok(list);
    }
 }
