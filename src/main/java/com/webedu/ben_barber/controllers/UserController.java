package com.webedu.ben_barber.controllers;

import com.webedu.ben_barber.annotation.TrackExecutionTime;
import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @TrackExecutionTime
    @Operation(description = "Está requizição faz o salvamento de um Usuário no banco de dados.", summary = "Realiza o salvamento de um Usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.info("Adding user: initiated");
        user = userService.addUser(user);
        log.info("Adding user: completed");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a Atualização de um Usuário no banco de dados.", summary = "Realiza a atualização de um Usuário", method = "PUT")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        log.info("Updating user: initiated");
        User updateUser = userService.updateUser(id, user);
        log.info("Updating user: completed");
        return ResponseEntity.ok(updateUser);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz A busca pelos Usuários já salvos no banco de dados.", summary = "Realiza a busca dos Usuários", method = "GET")
    @ApiResponse(responseCode = "200", description = "Usuários Retornados")
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        log.info("Finding all users: initiated");
        List<User> list = userService.findAllUsers();
        log.info("Finding all users: completed");
        return ResponseEntity.ok(list);
    }

    @TrackExecutionTime
    @Operation(description = "Está requisição faz a busca por ID dos Usuários já salvos no banco de dados.", summary = "Realiza a busca dos Usuários por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca Realizada."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        log.info("Finding user by id: initiated");
        User user = userService.findById(id);
        log.info("Finding user by id: completed");
        return ResponseEntity.ok(user);
    }

    @TrackExecutionTime
    @Operation(description = "Está requizição faz a deleção de um Usuário.", summary = "Realiza a deleção de um Usuário", method = "DELETE")
    @ApiResponse(responseCode = "200", description = "Usuário deletado")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        log.info("Deleting user by id: initiated");
        userService.delete(id);
        log.info("Deleting user by id: completed");
        return ResponseEntity.noContent().build();
    }
}
