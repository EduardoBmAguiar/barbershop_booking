 package com.webedu.ben_barber.controllers;

 import com.webedu.ben_barber.annotation.TrackExecutionTime;
 import com.webedu.ben_barber.entities.Agendate;
 import com.webedu.ben_barber.services.AgendateService;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

 import java.net.URI;
 import java.time.LocalDateTime;
 import java.util.List;

@Slf4j
@RestController
@RequestMapping("/agendates")
public class AgendateController {

    @Autowired
    AgendateService agendateService;

    @TrackExecutionTime
    @GetMapping
    public ResponseEntity<List<Agendate>> findAllAgendates() {
        log.info("Finding all Agendates: initiated");
        List<Agendate> list = agendateService.findAll();
        log.info("Finding all Agendates: completed");
        return ResponseEntity.ok(list);
    }

    @TrackExecutionTime
    @GetMapping(value = "/{id}")
    public ResponseEntity<Agendate> findAgendateById(@PathVariable Long id) {
        log.info("finding Agendate by Id: initiated");
        Agendate agendate = agendateService.findById(id);
        log.info("Finding Agendate by Id: completed");
        return ResponseEntity.ok(agendate);
    }

    @TrackExecutionTime
    @PostMapping
    public ResponseEntity<Agendate> addAgendate(@RequestBody Agendate agendate) {
        log.info("Adding Agendate: initiated");
        agendate = agendateService.addAgendate(agendate, agendate.getChosenDate());
        log.info("Adding Agendate: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(agendate.getId()).toUri();

        return ResponseEntity.created(uri).body(agendate);
    }

    @TrackExecutionTime
    @PutMapping(value = "/{id}")
    public ResponseEntity<Agendate> updateAgendate(@PathVariable Long id, @RequestBody Agendate agendate) {
        log.info("updating Agendate: initiated");
        agendate = agendateService.updateAgendate(id, agendate);
        log.info("updating Agendate: completed");
        return ResponseEntity.ok(agendate);
    }

    @TrackExecutionTime
    @GetMapping(value = "/hoursForAgendate")
    public ResponseEntity<List<LocalDateTime>> findAvailableHours(@RequestParam(value = "chosenDay") Integer chosenDay) {
        log.info("findAvailableHours: initiated");
        List<LocalDateTime> list = agendateService.findHoursAvailableOfDay(chosenDay);
        log.info("findAvailableHours: completed");
        return ResponseEntity.ok(list);
    }
 }
