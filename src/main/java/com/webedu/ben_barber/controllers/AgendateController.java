 package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.entities.Agendate;
import com.webedu.ben_barber.services.AgendateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/agendates")
public class AgendateController {

    @Autowired
    AgendateService agendateService;

    @PostMapping(value = "/{id}")
    public ResponseEntity<Agendate> addAgendate(@PathVariable Long id, @RequestBody Agendate agendate) {
        agendate = agendateService.addAgendate(id, agendate);
        return ResponseEntity.ok().body(agendate);
    }

}
