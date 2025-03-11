 package com.webedu.ben_barber.controllers;

 import com.webedu.ben_barber.entities.Agendate;
 import com.webedu.ben_barber.services.AgendateService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

 import java.net.URI;
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
}
