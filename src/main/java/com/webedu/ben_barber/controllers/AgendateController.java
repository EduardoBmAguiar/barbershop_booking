 package com.webedu.ben_barber.controllers;

 import com.webedu.ben_barber.entities.Agendate;
 import com.webedu.ben_barber.services.AgendateService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @Controller
@RequestMapping("/agendates")
public class AgendateController {

    @Autowired
    AgendateService agendateService;

    @GetMapping
    public ResponseEntity<List<Agendate>> findAllAgendates() {
        List<Agendate> list = agendateService.findAll();

        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Agendate> addAgendate(@PathVariable Long id, @RequestBody Agendate agendate) {
        agendate = agendateService.addAgendate(id, agendate);
        return ResponseEntity.ok().body(agendate);
    }

}
